package com.demo.security.service;

import com.demo.security.dto.color.ColorCreateDto;
import com.demo.security.dto.color.ColorResponseDto;
import com.demo.security.dto.make.MakeCreateDto;
import com.demo.security.dto.make.MakeResponseDto;
import com.demo.security.entity.Color;
import com.demo.security.entity.Model;
import com.demo.security.exception.ResourceNotFoundException;
import com.demo.security.exception.ValidationException;
import com.demo.security.mapper.ColorMapper;
import com.demo.security.repository.ColorRepository;
import com.demo.security.repository.ModelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ColorService {
    private final ModelRepository modelRepository;
    private final ColorRepository colorRepository;
    private final ColorMapper colorMapper;

    public List<ColorResponseDto> getColors(UUID modelId) {
        Model model = modelRepository.findById(modelId).orElseThrow(() -> new ResourceNotFoundException("Model " + modelId + " not found"));

        return model.getColors().stream().map(colorMapper::toColorResponseDto).toList();
    }

    public boolean createColor(String modelId, List<ColorCreateDto> colorCreateDto) {
        Model model = modelRepository.findById(UUID.fromString(modelId))
                .orElseThrow(() -> new ResourceNotFoundException("Model " + modelId + " not found"));

        for (ColorCreateDto colorDto : colorCreateDto) {
            // Check if the color already exists for this model
            boolean colorExists = colorRepository.existsByModelAndColorCode(model, colorDto.getColorCode());
            if (colorExists) {
                continue; // Skip saving if the color already exists
            }

            // Create and save new color
            Color newColor = new Color(
                    UUID.randomUUID(),
                    model,
                    colorDto.getColorCode(),
                    null,
                    null
            );
            colorRepository.save(newColor);
        }
        return true;
    }

    public ColorResponseDto updateColor(UUID id, UUID modelId, ColorCreateDto update) {
        // Find the model
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new ResourceNotFoundException("Model " + modelId + " not found"));

        // Find the color by ID
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color with ID " + id + " not found"));

        // Ensure the color belongs to the correct model
        if (!color.getModel().getId().equals(modelId)) {
            throw new ValidationException("Color does not belong to model " + modelId);
        }

        // Check if a color with the same code already exists (to prevent duplicates)
        if (colorRepository.existsByModelAndColorCode(model, update.getColorCode())) {
            throw new ValidationException("Color " + update.getColorCode() + " already exists for model " + modelId);
        }

        // Update the color code
        color.setColorCode(update.getColorCode());
        colorRepository.save(color);

        // Return updated model response
        return new ColorResponseDto(color.getId(), update.getColorCode());
    }


    public void deleteColor(UUID id, String modelId) {

        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color with ID " + id + " not found"));

        if (!color.getModel().getId().equals(UUID.fromString(modelId))) {
            throw new ValidationException("Color does not belong to model " + modelId);
        }

        colorRepository.delete(color);
    }
}
