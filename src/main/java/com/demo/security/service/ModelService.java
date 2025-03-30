package com.demo.security.service;

import com.demo.security.dto.color.ColorCreateDto;
import com.demo.security.dto.color.ColorResponseDto;
import com.demo.security.dto.image.ImageResponseDto;
import com.demo.security.dto.model.ModelCreateDto;
import com.demo.security.dto.model.ModelResponseDto;
import com.demo.security.dto.year.YearCreateDto;
import com.demo.security.dto.year.YearResponseDto;
import com.demo.security.entity.*;
import com.demo.security.exception.NotFoundException;
import com.demo.security.exception.ResourceNotFoundException;
import com.demo.security.mapper.ModelMapper;
import com.demo.security.repository.MakeRepository;
import com.demo.security.repository.ModelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ModelService {
    private final ModelRepository modelRepository;
    private final MakeRepository makeRepository;

    private final ModelMapper modelMapper;

    public List<ModelResponseDto> getAllActiveModels() {
        List<Model> activeModels = modelRepository.findAllByIsActiveTrue();

        return activeModels.stream().map(modelMapper::toModelResponseDto).toList();
    }

    public ModelResponseDto getSingleModel(UUID id) {
        Model model = modelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Model by " + id + " does not exist"));
        return modelMapper.toModelResponseDto(model);
    }

    public List<ModelResponseDto> getAllModels() {
        return modelRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    public ModelResponseDto getModelById(UUID modelId) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new NotFoundException("Model not found"));
        return convertToDto(model);
    }

    public void updateModel(UUID modelId, ModelCreateDto updateDto, MultipartFile[] images) throws IOException {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new NotFoundException("Model not found"));

        // Update basic fields
        model.setModelName(updateDto.getModelName());
        model.setModelDescription(updateDto.getModelDescription());
        model.setIsActive(updateDto.getIsActive());

        // Process Colors
        model.getColors().clear();
        model.getColors().addAll(processColors(updateDto.getColors(), model));

        // Process Years
        model.getYears().clear();
        model.getYears().addAll(processYears(updateDto.getYears(), model));

        // Process Images
        if (images != null && images.length > 0) {
            model.getImages().clear();
            model.getImages().addAll(processImages(images, model));
        }

        modelRepository.save(model);
    }

    public void deleteModel(UUID modelId) {
        Model model = modelRepository.findById(modelId)
                .orElseThrow(() -> new NotFoundException("Model not found"));
        modelRepository.delete(model);
    }

    private ModelResponseDto convertToDto(Model model) {
        return new ModelResponseDto(
                model.getId(),
                model.getModelName(),
                model.getModelDescription(),
                model.getColors().stream()
                        .map(color -> new ColorResponseDto(color.getId(), color.getColorCode()))
                        .toList(),
                model.getYears().stream()
                        .map(year -> new YearResponseDto(
                                year.getId(),
                                year.getModel().getId(),
                                year.getModel().getMake().getMakeName(),
                                year.getYear(),
                                year.getPrice(),
                                year.getTransmission(),
                                year.getFuelType(),
                                year.getEnginePower(),
                                year.getHorsePower(),
                                year.getTopSpeed(),
                                year.getFuelEconomy(),
                                year.getEmissions(),
                                model.getColors().stream()
                                        .map(color -> new ColorResponseDto(color.getId(), color.getColorCode()))
                                        .toList()
                        ))
                        .toList(),
                model.getImages().stream()
                        .map(img -> new ImageResponseDto(img.getId(), uriString(img.getId().toString())))
                        .toList(),
                model.getIsActive()
        );
    }

//    public boolean createModel(String modelId, List<YearCreateDto> yearCreateDto, MultipartFile[] images, UUID makeId) {
//        return true;
////        Model model = modelRepository.findById(UUID.fromString(modelId))
////                .orElseThrow(() -> new ResourceNotFoundException("Model not found"));
////
////        for (YearCreateDto yearDto : yearCreateDto) {
////            // Check if year exists
////            if (yearRepository.existsByModelAndYearAndTransmission(
////                    model, yearDto.getYear(), yearDto.getTransmission()
////            )) continue;
////
////            // Create Year and associate with Model
////            Year year = new Year(
////                    UUID.randomUUID(),
////                    model,
////                    yearDto.getModelName(),
////                    yearDto.getModelDescription(),
////                    yearDto.getYear(),
////                    yearDto.getPrice(),
////                    yearDto.getTransmission(),
////                    yearDto.getFuelType(),
////                    yearDto.getEnginePower(),
////                    yearDto.getHorsePower(),
////                    yearDto.getTopSpeed(),
////                    yearDto.getFuelEconomy(),
////                    yearDto.getEmissions(),
////                    new ArrayList<>(),  // Initialize images list
////                    null,
////                    null
////            );
////
////            // Process images
////            try {
////                for (MultipartFile file : images) {
////                    Image image = new Image(
////                            UUID.randomUUID(),
////                            file.getBytes(),
////                            file.getContentType(),
////                            StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())),
////                            year,  // Associate with Year
////                            null
////                    );
////                    year.getImages().add(image); // Add to Year's image list
////                }
////            } catch (IOException e) {
////                throw new ResourceNotFoundException("File upload failed: " + e.getMessage());
////            }
////
////            // Save Year (cascades to images)
////            yearRepository.save(year);
////        }
////        return true;
//    }

    public void createModel(ModelCreateDto modelCreateDto, MultipartFile[] images, UUID makeId) throws IOException {
        Make make = makeRepository.findById(makeId)
                .orElseThrow(() -> new NotFoundException("Make not found with id: " + makeId));

        Model model = new Model();
        model.setMake(make);
        model.setModelName(modelCreateDto.getModelName());
        model.setModelDescription(modelCreateDto.getModelDescription());
        model.setIsActive(modelCreateDto.getIsActive());

        // Process Colors
        model.setColors(processColors(modelCreateDto.getColors(), model));

        // Process Years
        model.setYears(processYears(modelCreateDto.getYears(), model));

        // Process Images
        model.setImages(processImages(images, model));

        modelRepository.save(model);
    }

    private List<Color> processColors(List<ColorCreateDto> colorDtos, Model model) {
        return colorDtos.stream()
                .map(dto -> {
                    Color color = new Color();
                    color.setColorCode(dto.getColorCode());
                    color.setModel(model);
                    return color;
                }).toList();
    }

    private List<Year> processYears(List<YearCreateDto> yearDtos, Model model) {
        return yearDtos.stream()
                .map(dto -> {
                    Year year = new Year();
//                    year.setModelName(dto.getModelName());
//                    year.setModelDescription(dto.getModelDescription());
                    year.setYear(dto.getYear());
                    year.setPrice(dto.getPrice());
                    year.setTransmission(dto.getTransmission());
                    year.setFuelType(dto.getFuelType());
                    year.setEnginePower(dto.getEnginePower());
                    year.setHorsePower(dto.getHorsePower());
                    year.setTopSpeed(dto.getTopSpeed());
                    year.setFuelEconomy(dto.getFuelEconomy());
                    year.setEmissions(dto.getEmissions());
                    year.setModel(model);
                    return year;
                }).toList();
    }

    private List<Image> processImages(MultipartFile[] images, Model model) throws IOException {
        List<Image> imageEntities = new ArrayList<>();

        for (MultipartFile file : images) {
            if (!file.isEmpty()) {
                Image image = new Image();
                image.setId(UUID.randomUUID());
                image.setBytes(file.getBytes());
                image.setFileType(file.getContentType());
                image.setFileName(file.getOriginalFilename());
                image.setModel(model);
                imageEntities.add(image);
            }
        }
        return imageEntities;
    }

    private String uriString(String id) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/public/image/download/")
                .path(id)
                .toUriString();
    }
}
