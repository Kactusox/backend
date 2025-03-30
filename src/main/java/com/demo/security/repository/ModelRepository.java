package com.demo.security.repository;

import com.demo.security.entity.Make;
import com.demo.security.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModelRepository extends JpaRepository<Model, UUID> {
//    List<Model> findByIsActiveTrue();
    List<Model> findAllByIsActiveTrue();
}
