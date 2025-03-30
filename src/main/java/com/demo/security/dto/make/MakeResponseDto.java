package com.demo.security.dto.make;

import com.demo.security.dto.model.ModelResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeResponseDto {
    private UUID id;
    private String makeName;
    private boolean isActive;
    private List<ModelResponseDto> models;
}
