package com.demo.security.dto.year;

import com.demo.security.dto.color.ColorResponseDto;
import com.demo.security.enums.FuelType;
import com.demo.security.enums.TransmissionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearResponseDto {
    private UUID id;
    private UUID modelId;
    private String makeName;

    private Integer year;

    private BigDecimal price;

    private TransmissionType transmission;

    private FuelType fuelType;

    private Integer enginePower;

    private Integer horsePower;

    private Integer topSpeed;

    private Integer fuelEconomy;

    private Integer emissions;

    private List<ColorResponseDto> colors;
}
