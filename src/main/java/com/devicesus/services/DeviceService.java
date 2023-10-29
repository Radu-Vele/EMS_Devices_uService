package com.devicesus.services;

import com.devicesus.dto.DeviceCreatedDto;
import com.devicesus.dto.DeviceDto;
import com.devicesus.entities.Device;
import com.devicesus.repositories.DeviceRepository;
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

    public UUID create(DeviceCreatedDto deviceCreatedDto) {
        return deviceRepository
                .save(mapper.map(deviceCreatedDto, Device.class))
                .getId();
    }

    public Void deleteById(String id) throws NoSuchElementException {
        deviceRepository.deleteById(UUID.fromString(id));
        return null;
    }

    public List<DeviceDto> getAll() {
        return this.deviceRepository.findAll().stream()
                .map(device -> this.mapper.map(device, DeviceDto.class))
                .toList();
    }

    public Void edit(DeviceDto deviceNewData) throws NoSuchElementException{
        Device device = this.deviceRepository.findById(UUID.fromString(deviceNewData.getId()))
                .orElseThrow(() -> new NoSuchElementException(DEVICE_NOT_FOUND + deviceNewData.getId()));
        device.setAddress(deviceNewData.getAddress());
        device.setDescription(deviceNewData.getDescription());
        device.setMaxHourlyEnergyConsumption(deviceNewData.getMaxHourlyEnergyConsumption());
        return null;
    }
}
