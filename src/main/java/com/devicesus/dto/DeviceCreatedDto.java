package com.devicesus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCreatedDto {
    private String description;
    private String address;
    // add IP address validator (IPv6 or IPv4)
    private Long maxHourlyEnergyConsumption;
}
