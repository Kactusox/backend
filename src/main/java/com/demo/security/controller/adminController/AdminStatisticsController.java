package com.demo.security.controller.adminController;

import com.demo.security.dto.statistics.StatisticsResponseDto;
import com.demo.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/statistics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminStatisticsController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<StatisticsResponseDto> getAllStatistics() {
        return ResponseEntity.ok(userService.getAllStatistics());
    }
}
