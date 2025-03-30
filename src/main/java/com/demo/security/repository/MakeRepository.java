package com.demo.security.repository;

import com.demo.security.entity.Make;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MakeRepository extends JpaRepository<Make, UUID> {
    Optional<Make> findByMakeName(String makeName);

    List<Make> findByIsActiveTrue();

    List<Make> findByIsActiveFalse();

    @Query("SELECT m FROM Make m WHERE m.isActive = true AND LOWER(m.makeName) LIKE LOWER(CONCAT('%', :makeName, '%'))")
    List<Make> searchActiveMakes(@Param("makeName") String makeName);

    @Query("SELECT m FROM Make m WHERE m.isActive = false AND LOWER(m.makeName) LIKE LOWER(CONCAT('%', :makeName, '%'))")
    List<Make> searchNonActiveMakes(@Param("makeName") String makeName);

    @Query("SELECT m FROM Make m WHERE  LOWER(m.makeName) LIKE LOWER(CONCAT('%', :makeName, '%'))")
    List<Make> searchAllMakes(@Param("makeName") String makeName);
    boolean existsByMakeNameAndIdNot(String makeName, UUID id);
}
