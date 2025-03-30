package com.demo.security.mapper;

import com.demo.security.dto.image.ImageResponseDto;
import com.demo.security.dto.model.ModelResponseDto;
import com.demo.security.entity.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class ModelMapper {
    private final YearMapper yearMapper;
    private final ColorMapper colorMapper;

    public ModelResponseDto toModelResponseDto(Model model) {
        return new ModelResponseDto(
                model.getId(),
                model.getModelName(),
                model.getModelDescription(),
                model.getColors().stream()
                        .map(colorMapper::toColorResponseDto)
                        .toList(),
                model.getYears().stream()
                        .map(year -> yearMapper.toYearResponseDto(year, model))
                        .toList(),
                model.getImages().stream().map(image -> new ImageResponseDto(
                        image.getId(),
                        uriString(image.getId().toString()))
                ).toList(),
                model.getIsActive()
        );
    }

    private String uriString(String id) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/public/image/download/")
                .path(id)
                .toUriString();
    }
}
