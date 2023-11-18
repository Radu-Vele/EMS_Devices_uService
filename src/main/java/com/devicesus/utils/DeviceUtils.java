package com.devicesus.utils;

import com.devicesus.dto.DeviceChangeMessageDto;
import com.devicesus.entities.Device;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class DeviceUtils {
    public static String formatDeviceChangeToJsonString(ObjectMapper objectMapper, Device changedDevice) throws JsonProcessingException {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper.writeValueAsString(DeviceChangeMessageDto.builder()
                .deviceId(changedDevice.getId())
                .maxHourlyConsumption(changedDevice.getMaxHourlyEnergyConsumption())
                .ownerId(changedDevice.getOwner() == null ? null : changedDevice.getOwner().getUserId()));
    }
}
