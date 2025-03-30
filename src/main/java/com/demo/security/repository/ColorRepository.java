package com.demo.security.repository;

import com.demo.security.entity.Color;
import com.demo.security.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ColorRepository extends JpaRepository<Color, UUID> {
    boolean existsByModelAndColorCode(Model model, String colorCode);

    List<Color> findByModel(Model model);
}
