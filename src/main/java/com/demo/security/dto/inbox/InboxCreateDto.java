package com.demo.security.dto.inbox;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InboxCreateDto {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Email is required")
    private String email;
}
