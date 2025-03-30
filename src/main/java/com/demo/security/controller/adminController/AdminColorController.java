package com.demo.security.controller.adminController;

import com.demo.security.dto.color.ColorCreateDto;
import com.demo.security.dto.color.ColorResponseDto;
import com.demo.security.service.ColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/color")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminColorController {
    private final ColorService colorService;

    @PostMapping("/{modelId}")
    public ResponseEntity<String> createColor(@Valid @RequestBody List<ColorCreateDto> colorCreateDto, @PathVariable String modelId) {
        if (colorService.createColor(modelId, colorCreateDto))
            return ResponseEntity.status(HttpStatus.CREATED).body("Colors created");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
    }

    @PutMapping("/{modelId}/{id}")
    public ResponseEntity<ColorResponseDto> updateColor(@PathVariable UUID id, @PathVariable UUID modelId, @Valid @RequestBody ColorCreateDto update) {
        return ResponseEntity.ok(colorService.updateColor(id, modelId, update));
    }

    @DeleteMapping("/{modelId}/{id}")
    public ResponseEntity<String> deleteColor(@PathVariable UUID id, @PathVariable String modelId) {
        colorService.deleteColor(id, modelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Color deleted");
    }
}
