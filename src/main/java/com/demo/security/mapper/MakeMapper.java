package com.demo.security.mapper;

import com.demo.security.dto.make.MakeResponseDto;
import com.demo.security.entity.Make;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class MakeMapper {
    private final ModelMapper modelMapper;

    public MakeResponseDto toMakeResponseDto(Make make) {
        return new MakeResponseDto(
                make.getId(),
                make.getMakeName(),
                make.getIsActive(),
                make.getModels().stream()
                        .map(modelMapper::toModelResponseDto)
                        .toList()
        );
    }
}

