package com.demo.security.repository;

import com.demo.security.entity.Model;
import com.demo.security.entity.Year;
import com.demo.security.enums.TransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface YearRepository extends JpaRepository<Year, UUID> {
    boolean existsByModelAndYearAndTransmission(Model model, Integer year, TransmissionType transmission);
}
