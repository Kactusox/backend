package com.demo.security.user;

import com.demo.security.dto.statistics.StatisticsResponseDto;
import com.demo.security.dto.user.UserResponseDto;
import com.demo.security.dto.user.UserUpdateDto;
import com.demo.security.exception.NotFoundException;
import com.demo.security.mapper.UserMapper;
import com.demo.security.repository.MakeRepository;
import com.demo.security.repository.ModelRepository;
import com.demo.security.token.Token;
import com.demo.security.token.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserRepository userRepository;
    private final MakeRepository makeRepository;
    private final ModelRepository modelRepository;
    private final UserMapper userMapper;
    private final TokenRepository tokenRepository;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public StatisticsResponseDto getAllStatistics() {
        int admins = userRepository.findAllAdminUsers().size();
        int users = userRepository.findAllRegularUsers().size();
        int models = modelRepository.findAll().size();
        int makes = makeRepository.findAll().size();

        return new StatisticsResponseDto(users, admins, makes, models);
    }

    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toUserResponseDto).toList();
    }

    public UserResponseDto updateUserAdmin(Integer id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        user.setRole(userUpdateDto.getRole());
        user.setFirstname(userUpdateDto.getFirstname());
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        user.setIsActive(userUpdateDto.getIsActive());

        userRepository.save(user);
        return userMapper.toUserResponseDto(repository.save(user));
    }

    public void deleteUser(Integer id) {
        tokenRepository.deleteAllByUserId(id);
        userRepository.deleteById(id);
    }
}















