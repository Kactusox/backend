package com.demo.security.controller.adminController;

import com.demo.security.dto.model.ModelCreateDto;
import com.demo.security.dto.model.ModelResponseDto;
import com.demo.security.exception.NotFoundException;
import com.demo.security.exception.ResourceNotFoundException;
import com.demo.security.service.ModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/model")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminModelController {
    private final ModelService modelService;

    @PostMapping(value = "/{makeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createModel(
            @RequestPart @Valid ModelCreateDto modelCreateDto,
            @RequestPart("images") MultipartFile[] images,
            @PathVariable UUID makeId) {
        try {
            modelService.createModel(modelCreateDto, images, makeId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Model created successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File processing error");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ModelResponseDto>> getAllModels() {
        return ResponseEntity.ok(modelService.getAllModels());
    }

    // GET Single Model
    @GetMapping("/{modelId}")
    public ResponseEntity<ModelResponseDto> getModelById(@PathVariable UUID modelId) {
        return ResponseEntity.ok(modelService.getModelById(modelId));
    }

    // UPDATE Model
    @PutMapping(value = "/{modelId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateModel(
            @PathVariable UUID modelId,
            @RequestPart @Valid ModelCreateDto modelUpdateDto,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {
        try {
            modelService.updateModel(modelId, modelUpdateDto, images);
            return ResponseEntity.ok("Model updated successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File processing error");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request: " + e.getMessage());
        }
    }

    // DELETE Model
    @DeleteMapping("/{modelId}")
    public ResponseEntity<String> deleteModel(@PathVariable UUID modelId) {
        try {
            modelService.deleteModel(modelId);
            return ResponseEntity.ok("Model deleted successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

