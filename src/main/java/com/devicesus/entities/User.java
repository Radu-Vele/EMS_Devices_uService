package com.devicesus.entities;

import com.devicesus.dto.DeviceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    UUID id; // TODO: can remove and only use the user id as Id

    @Column(nullable = false, unique = true)
    UUID userId; // consistent with users microservice data

    @OneToMany(fetch = FetchType.LAZY)
    List<Device> devices;

    public User(UUID userId) {
        this.userId = userId;
        this.devices = new ArrayList<>();
    }
}
