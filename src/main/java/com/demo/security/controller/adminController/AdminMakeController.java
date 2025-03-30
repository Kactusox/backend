package com.demo.security.controller.adminController;


import com.demo.security.dto.make.MakeCreateDto;
import com.demo.security.dto.make.MakeResponseDto;
import com.demo.security.dto.make.MakeUpdateDto;
import com.demo.security.service.MakeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/make")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminMakeController {
    private final MakeService makeService;

    @PostMapping
    public ResponseEntity<String> createMake(@Valid @RequestBody MakeCreateDto makeCreateDto) {
        if (makeService.createMake(makeCreateDto))
            return ResponseEntity.status(HttpStatus.CREATED).body(makeCreateDto.getMakeName() + " created");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
    }

    @GetMapping("/all")
    public ResponseEntity<List<MakeResponseDto>> getAllMakes(@RequestParam(required = false) String makeName) {
        return ResponseEntity.ok(makeService.getAllMakes(makeName));
    }

    @GetMapping("/all-non-active")
    public ResponseEntity<List<MakeResponseDto>> getAllNonActiveMakes(@RequestParam(required = false) String makeName) {
        return ResponseEntity.ok(makeService.getAllNonActiveMakes(makeName));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MakeResponseDto> updateMake(@PathVariable UUID id, @RequestBody MakeUpdateDto update) {
        return ResponseEntity.ok(makeService.updateMake(id, update));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMake(@PathVariable UUID id) {
        makeService.deleteMake(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Make deleted");
    }
}
