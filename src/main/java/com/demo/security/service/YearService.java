package com.demo.security.service;

import com.demo.security.dto.year.YearCreateDto;
import com.demo.security.dto.year.YearResponseDto;
import com.demo.security.entity.Image;
import com.demo.security.entity.Model;
import com.demo.security.entity.Year;
import com.demo.security.exception.ResourceNotFoundException;
import com.demo.security.exception.ValidationException;
import com.demo.security.mapper.YearMapper;
import com.demo.security.repository.ModelRepository;
import com.demo.security.repository.YearRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class YearService {
    private final YearRepository yearRepository;
    private final ModelRepository modelRepository;

    private final YearMapper yearMapper;

    public List<YearResponseDto> getYears(UUID modelId) {
        Model model = modelRepository.findById(modelId).orElseThrow(() -> new ResourceNotFoundException("Model " + modelId + " not found"));

        return model.getYears().stream().map(year -> yearMapper.toYearResponseDto(year, model)).toList();
    }

    public boolean createYear(String modelId, List<YearCreateDto> yearCreateDto) {
        Model model = modelRepository.findById(UUID.fromString(modelId))
                .orElseThrow(() -> new ResourceNotFoundException("Model not found"));

        for (YearCreateDto yearDto : yearCreateDto) {
            // Check if year exists
            if (yearRepository.existsByModelAndYearAndTransmission(
                    model, yearDto.getYear(), yearDto.getTransmission()
            )) continue;

            // Create Year and associate with Model
            Year year = new Year(
                    UUID.randomUUID(),
                    model,
//                    yearDto.getModelName(),
//                    yearDto.getModelDescription(),
                    yearDto.getYear(),
                    yearDto.getPrice(),
                    yearDto.getTransmission(),
                    yearDto.getFuelType(),
                    yearDto.getEnginePower(),
                    yearDto.getHorsePower(),
                    yearDto.getTopSpeed(),
                    yearDto.getFuelEconomy(),
                    yearDto.getEmissions(),
                    null,
                    null
            );

            yearRepository.save(year);
        }
        return true;
    }

    public YearResponseDto updateYear(UUID id, UUID modelId, YearCreateDto update) {
        // Find the model by modelId
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new ResourceNotFoundException("Model " + modelId + " not found"));

        // Find the year by id and ensure it belongs to the model
        Year year = yearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Year with ID " + id + " not found"));

        if (!year.getModel().getId().equals(modelId)) {
            throw new IllegalArgumentException("Year does not belong to model " + modelId);
        }

        // Check for duplicate year and transmission (skip if updating same values)
        if (yearRepository.existsByModelAndYearAndTransmission(model, update.getYear(), update.getTransmission())
                && !year.getYear().equals(update.getYear())) {
            throw new ValidationException("Year " + update.getYear() + " already exists for model " + modelId);
        }

        // Update year properties
        year.setYear(update.getYear());
        year.setPrice(update.getPrice());
        year.setTransmission(update.getTransmission());
        year.setFuelType(update.getFuelType());
        year.setEnginePower(update.getEnginePower());
        year.setHorsePower(update.getHorsePower());
        year.setTopSpeed(update.getTopSpeed());
        year.setFuelEconomy(update.getFuelEconomy());
        year.setEmissions(update.getEmissions());

        yearRepository.save(year);

        return yearMapper.toYearResponseDto(year, model);
    }


    public void deleteYear(UUID id, String modelId) {
        // Find the year by id
        Year year = yearRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Year with ID " + id + " not found"));

        // Ensure the year belongs to the provided model
        if (!year.getModel().getId().equals(UUID.fromString(modelId))) {
            throw new ValidationException("Year does not belong to model " + modelId);
        }

        yearRepository.delete(year);
    }

    public List<YearResponseDto> getAllYears() {
        List<Year> years = yearRepository.findAll();
        return years.stream().map(year -> yearMapper.toYearResponseDto(year, year.getModel())).toList();
    }
}
