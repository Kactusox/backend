package com.demo.security.dto.color;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorResponseDto {
    private UUID id;
    private String colorCode;
}
