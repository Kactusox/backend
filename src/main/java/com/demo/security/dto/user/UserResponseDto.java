package com.demo.security.dto.user;

import com.demo.security.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Integer id;
    private String firstname;
    private String phoneNumber;
    private Role role;
    private Boolean isActive;
    private LocalDateTime created;
    private LocalDateTime updated;
}
