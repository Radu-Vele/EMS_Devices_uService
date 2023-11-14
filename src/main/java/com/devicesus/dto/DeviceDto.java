package com.devicesus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto{
    private String id;
    private String description;
    private String address;
    // add IP address validator (IPv6 or IPv4)
    private Double maxHourlyEnergyConsumption;
}
