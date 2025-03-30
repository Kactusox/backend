package com.demo.security.dto.model;

import com.demo.security.dto.color.ColorResponseDto;
import com.demo.security.dto.image.ImageResponseDto;
import com.demo.security.dto.year.YearResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelResponseDto {
    private UUID id;
    private String modelName;
    private String modelDescription;
    private List<ColorResponseDto> colors;
    private List<YearResponseDto> years;
    private List<ImageResponseDto> images;
    private Boolean isActive;
}
