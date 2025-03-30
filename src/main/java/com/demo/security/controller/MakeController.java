package com.demo.security.controller;

import com.demo.security.dto.make.MakeResponseDto;
import com.demo.security.service.MakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/public/make")
@RequiredArgsConstructor
public class MakeController {
    private final MakeService makeService;

    @GetMapping("/all-active")
    public ResponseEntity<List<MakeResponseDto>> getAllActiveMakes(@RequestParam(required = false) String makeName) {
        return ResponseEntity.ok(makeService.getAllActiveMakes(makeName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MakeResponseDto> getSingleMake(@PathVariable UUID id) {
        return ResponseEntity.ok(makeService.getSingleMake(id));
    }
}

