package com.demo.security.controller;

import com.demo.security.dto.make.MakeResponseDto;
import com.demo.security.dto.model.ModelResponseDto;
import com.demo.security.service.MakeService;
import com.demo.security.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/public/model")
@RequiredArgsConstructor
public class ModelController {
    private final ModelService modelService;

    @GetMapping("/all-active")
    public ResponseEntity<List<ModelResponseDto>> getAllActiveModels() {
        return ResponseEntity.ok(modelService.getAllActiveModels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelResponseDto> getSingleModel(@PathVariable UUID id) {
        return ResponseEntity.ok(modelService.getSingleModel(id));
    }
}
