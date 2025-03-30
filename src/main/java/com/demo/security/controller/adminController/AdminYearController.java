package com.demo.security.controller.adminController;

import com.demo.security.dto.year.YearCreateDto;
import com.demo.security.dto.year.YearResponseDto;
import com.demo.security.service.YearService;
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
@RequestMapping("/admin/year")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminYearController {
    private final YearService yearService;

    //    @PostMapping("/{modelId}")
//    public ResponseEntity<String> createYear(@Valid @ModelAttribute List<YearCreateDto> yearCreateDto, @PathVariable String modelId) {
//        if (yearService.createYear(modelId, yearCreateDto))
//            return ResponseEntity.status(HttpStatus.CREATED).body("Years created");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
//    }
    @PostMapping(value = "/{modelId}")
    public ResponseEntity<String> createYear(
            @PathVariable String modelId,
            @RequestPart("years") @Valid List<YearCreateDto> yearCreateDto
    ) {
        if (yearService.createYear(modelId, yearCreateDto)) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Years created");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
    }

    @PutMapping("/{modelId}/{id}")
    public ResponseEntity<YearResponseDto> updateYear(@PathVariable UUID id, @PathVariable UUID modelId, @Valid @RequestBody YearCreateDto update) {
        return ResponseEntity.ok(yearService.updateYear(id, modelId, update));
    }

    @DeleteMapping("/{modelId}/{id}")
    public ResponseEntity<String> deleteYear(@PathVariable UUID id, @PathVariable String modelId) {
        yearService.deleteYear(id, modelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Year deleted");
    }
}
