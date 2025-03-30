package com.demo.security.repository;

import com.demo.security.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
//    List<Image> findByYearId(UUID modelId);
}

