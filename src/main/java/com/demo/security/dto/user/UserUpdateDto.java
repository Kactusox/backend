package com.demo.security.dto.user;

import com.demo.security.user.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {
    @NotBlank(message = "Username is required")
    private String firstname;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    private Role role;
    private Boolean isActive;
}
