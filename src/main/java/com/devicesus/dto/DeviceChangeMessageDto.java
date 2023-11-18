package com.devicesus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceChangeMessageDto {
    private UUID deviceId;
    private Double maxHourlyConsumption;
    private UUID ownerId;
}
