package com.demo.security.controller;

import com.demo.security.dto.color.ColorResponseDto;
import com.demo.security.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/public/color")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;


    @GetMapping("/{modelId}")
    public ResponseEntity<List<ColorResponseDto>> getColors(@PathVariable UUID modelId) {
        return ResponseEntity.ok(colorService.getColors(modelId));
    }
}
