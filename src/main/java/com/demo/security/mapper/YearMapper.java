package com.demo.security.mapper;

import com.demo.security.dto.year.YearResponseDto;
import com.demo.security.entity.Model;
import com.demo.security.entity.Year;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@Component
@RequiredArgsConstructor
public class YearMapper {
    private final ColorMapper colorMapper;

    public YearResponseDto toYearResponseDto(Year year, Model model) {
        return new YearResponseDto(
                year.getId(),
                model.getId(),
                model.getMake().getMakeName(),
//                year.getModelName(),
//                year.getModelDescription(),
                year.getYear(),
                year.getPrice(),
                year.getTransmission(),
                year.getFuelType(),
                year.getEnginePower(),
                year.getHorsePower(),
                year.getTopSpeed(),
                year.getFuelEconomy(),
                year.getEmissions(),
//                year.getImages().stream().map(image -> new ImageResponseDto(
//                        image.getId(),
//                        uriString(image.getId().toString()))
//                ).toList().toArray(new ImageResponseDto[0]),
                model.getColors().stream().map(colorMapper::toColorResponseDto).toList()
        );
    }
}
