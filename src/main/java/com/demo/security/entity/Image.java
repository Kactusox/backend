package com.demo.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "images")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Lob
    @Column(name = "image_data", nullable = false)
    private byte[] bytes;  // Store image as bytes

    @Column(name = "file_type", nullable = false)
    private String fileType; //  "image/png"

    @Column(name = "file_name", nullable = false)
    private String fileName; // Original file name

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

}
