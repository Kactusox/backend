package com.demo.security.mapper;

import com.demo.security.dto.user.UserResponseDto;
import com.demo.security.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto toUserResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstname(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getIsActive(),
                user.getCreated(),
                user.getUpdated()
        );
    }
}
