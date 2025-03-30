package com.demo.security.dto.make;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeUpdateDto {
    @NotBlank(message = "Make name is required")
    private String makeName;
    private Boolean isActive;
}
