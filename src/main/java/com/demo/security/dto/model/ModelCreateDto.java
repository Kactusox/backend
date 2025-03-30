package com.demo.security.dto.model;

import com.demo.security.dto.color.ColorCreateDto;
import com.demo.security.dto.year.YearCreateDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelCreateDto {

    @NotBlank(message = "Model name is required")
    private String modelName;

    @NotBlank(message = "Model description is required")
    private String modelDescription;

    @NotNull(message = "Color is required")
    private List<ColorCreateDto> colors;

    @NotNull(message = "Year is required")
    private List<YearCreateDto> years;

    private Boolean isActive = true;
}
