package com.demo.security.controller;

import com.demo.security.dto.year.YearResponseDto;
import com.demo.security.service.YearService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/public/year")
@RequiredArgsConstructor
public class YearController {
    private final YearService yearService;

    @GetMapping
    public ResponseEntity<List<YearResponseDto>> getAllYears() {
        return ResponseEntity.ok(yearService.getAllYears());
    }

    @GetMapping("/{modelId}")
    public ResponseEntity<List<YearResponseDto>> getYears(@PathVariable UUID modelId) {
        return ResponseEntity.ok(yearService.getYears(modelId));
    }
}
