package com.demo.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inbox")
public class Inbox {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "full_name", nullable = false)
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Lob
    @Size(max = 10485760, message = "Description too long (max 10MB)")
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "Email is required")
    private String email;

    @CreationTimestamp
    @Column(name = "created", updatable = false)
    private Timestamp created;
}
