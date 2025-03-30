package com.demo.security.mapper;

import com.demo.security.dto.color.ColorResponseDto;
import com.demo.security.entity.Color;
import org.springframework.stereotype.Component;

@Component
public class ColorMapper {
    public ColorResponseDto toColorResponseDto(Color color) {
        return new ColorResponseDto(
                color.getId(),
                color.getColorCode()
        );
    }
}
