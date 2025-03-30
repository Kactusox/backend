package com.demo.security.controller.adminController;

import com.demo.security.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/image")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminImageController {
    private final ImageService imageService;
}
