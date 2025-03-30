package com.demo.security.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsResponseDto {
    private Integer users;
    private Integer admins;
    private Integer totalMakes;
    private Integer totalModels;
}
