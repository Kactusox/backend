package com.demo.security.entity;

import com.demo.security.enums.FuelType;
import com.demo.security.enums.TransmissionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "year")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Year {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

//    @Column(name = "model_name", nullable = false)
//    @NotBlank(message = "Model name is required")
//    private String modelName;
//
//    @Column(name = "model_description", nullable = false, length = 8192)
//    @NotBlank(message = "Model description is required")
//    private String modelDescription;

    @Column(name = "year", nullable = false)
    @NotNull(message = "Year is required")
    private Integer year;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Price is required")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "transmission", nullable = false)
    @NotNull(message = "Transmission is required")
    private TransmissionType transmission;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;

    @Column(name = "engine_power")
    @NotNull(message = "Engine power is required")
    private Integer enginePower;

    @Column(name = "horse_power")
    @NotNull(message = "Horsepower is required")
    private Integer horsePower;

    @Column(name = "top_speed")
    @NotNull(message = "Top speed is required")
    private Integer topSpeed;

    @Column(name = "fuel_economy")
    @NotNull(message = "Fuel economy is required")
    private Integer fuelEconomy;

    @Column(name = "emissions")
    @NotNull(message = "Emissions is required")
    private Integer emissions;

//    @OneToMany(mappedBy = "year", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Image> images = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
