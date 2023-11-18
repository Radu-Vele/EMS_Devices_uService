package com.devicesus.services;

import com.devicesus.dto.DeviceChangeMessageDto;
import com.devicesus.dto.DeviceCreatedDto;
import com.devicesus.dto.DeviceDto;
import com.devicesus.entities.Device;
import com.devicesus.messaging.DeviceChangeSender;
import com.devicesus.repositories.DeviceRepository;
import com.devicesus.utils.DeviceUtils;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.devicesus.constants.ExceptionMessages.DEVICE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class DeviceService {
    private final ModelMapper mapper;
    private final DeviceRepository deviceRepository;
    private final DeviceChangeSender deviceChangeSender;
    private final ObjectMapper objectMapper;

    public UUID create(DeviceCreatedDto deviceCreatedDto) throws JsonProcessingException {
        Device createdDevice = deviceRepository
                .save(mapper.map(deviceCreatedDto, Device.class));
        this.deviceChangeSender.send(
                DeviceUtils.formatDeviceChangeToJsonString(this.objectMapper, createdDevice));
        return createdDevice.getId();
    }

    public Void deleteById(String id) throws NoSuchElementException, JsonProcessingException {
        // TODO: [bug if I call when device belongs to user] add mechanism for removing device from user
        deviceRepository.deleteById(UUID.fromString(id));
        deviceChangeSender.send(DeviceUtils.formatDeviceChangeToJsonString(this.objectMapper, Device.builder()
                .id(UUID.fromString(id))
                .maxHourlyEnergyConsumption(-1.0)
                .build()
        ));
        return null;
    }

    public List<DeviceDto> getAll() {
        return this.deviceRepository.findAll().stream()
                .map(device -> this.mapper.map(device, DeviceDto.class))
                .toList();
    }

    public Void edit(DeviceDto deviceNewData) throws NoSuchElementException, JsonProcessingException {
        Device device = this.deviceRepository.findById(UUID.fromString(deviceNewData.getId()))
                .orElseThrow(() -> new NoSuchElementException(DEVICE_NOT_FOUND + deviceNewData.getId()));
        boolean sendUpdate = deviceNewData.getMaxHourlyEnergyConsumption() != device.getMaxHourlyEnergyConsumption();
        device.setAddress(deviceNewData.getAddress());
        device.setDescription(deviceNewData.getDescription());
        device.setMaxHourlyEnergyConsumption(deviceNewData.getMaxHourlyEnergyConsumption());
        if (sendUpdate) {
            this.deviceChangeSender.send(DeviceUtils.formatDeviceChangeToJsonString(this.objectMapper, device));
        }
        return null;
    }

    public DeviceDto getDetailsById(String id) {
        Device device = this.deviceRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NoSuchElementException(DEVICE_NOT_FOUND + id));
        return mapper.map(device, DeviceDto.class);
    }
}
