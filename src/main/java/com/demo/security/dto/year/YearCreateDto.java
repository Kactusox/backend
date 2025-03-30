package com.demo.security.dto.year;

import com.demo.security.enums.FuelType;
import com.demo.security.enums.TransmissionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearCreateDto {

    @NotNull(message = "Year is required")
    private Integer year;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "Transmission is required")
    private TransmissionType transmission;

    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;

    @NotNull(message = "Engine power is required")
    private Integer enginePower;

    @NotNull(message = "Horse power is required")
    private Integer horsePower;

    @NotNull(message = "Top speed is required")
    private Integer topSpeed;

    @NotNull(message = "Fuel economy is required")
    private Integer fuelEconomy;

    @NotNull(message = "Emissions is required")
    private Integer emissions;
}
