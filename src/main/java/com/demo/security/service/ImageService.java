package com.demo.security.service;

import com.demo.security.entity.Image;
import com.demo.security.exception.ResourceNotFoundException;
import com.demo.security.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public void deleteImage(UUID id) {
        imageRepository.deleteById(id);
    }

    public Image getImage(UUID imageId) {
        return imageRepository
                .findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find image with id - " + imageId));
    }

    private String uriString(String id) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/public/download/")
                .path(id)
                .toUriString();
    }
}
