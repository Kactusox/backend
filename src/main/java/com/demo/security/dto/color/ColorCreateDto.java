package com.demo.security.dto.color;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorCreateDto {
    @NotBlank(message = "Color is required")
    private String colorCode;
}
