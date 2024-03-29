package com.devicesus.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String description;

    private String address;

    @Column(nullable = false)
    private Double maxHourlyEnergyConsumption;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner = null;
}
