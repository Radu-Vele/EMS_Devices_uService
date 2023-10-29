package com.devicesus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDeviceMappingDto {
    String userId;
    String deviceId;
}
