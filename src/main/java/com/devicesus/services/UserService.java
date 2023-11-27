package com.devicesus.services;

import com.devicesus.dto.DeviceDto;
import com.devicesus.dto.UserDeviceMappingDto;
import com.devicesus.entities.Device;
import com.devicesus.entities.User;
import com.devicesus.messaging.DeviceChangeSender;
import com.devicesus.repositories.DeviceRepository;
import com.devicesus.repositories.UserRepository;
import com.devicesus.utils.DeviceUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.devicesus.constants.ExceptionMessages.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final ModelMapper mapper;
    private final DeviceChangeSender deviceChangeSender;
    private final ObjectMapper objectMapper;

    public UUID register(String id) {
        return this.userRepository.save(new User(UUID.fromString(id)))
                .getUserId();
    }

    public Void delete(String userId) throws JsonProcessingException {
        User user = this.userRepository.findByUserId(UUID.fromString(userId))
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND + userId));
        user.getDevices()
                .forEach((device) -> {
                    device.setOwner(null);
                    try {
                        this.deviceChangeSender.send(DeviceUtils.formatDeviceChangeToJsonString(this.objectMapper, device));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace(); // TODO: fix (maybe get rid of functional style)
                    }
                });
        this.userRepository.delete(user);
        return null;
    }

    public Void addDeviceToUser(UserDeviceMappingDto userDeviceMappingDto) throws JsonProcessingException {
        User user = this.userRepository.findByUserId(UUID.fromString(userDeviceMappingDto.getUserId()))
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND + userDeviceMappingDto.getUserId()));
        Device device = this.deviceRepository.findById(UUID.fromString(userDeviceMappingDto.getDeviceId()))
                .orElseThrow(() -> new NoSuchElementException(DEVICE_NOT_FOUND + userDeviceMappingDto.getDeviceId()));
        if (device.getOwner() != null) {
            throw new IllegalStateException(DEVICE_NOT_AVAILABLE + device.getDescription());
        }
        user.getDevices().add(device);
        device.setOwner(user);
        this.deviceChangeSender.send(DeviceUtils.formatDeviceChangeToJsonString(this.objectMapper, device));
        return null;
    }

    public Void removeDeviceFromUser(UserDeviceMappingDto userDeviceMappingDto) throws JsonProcessingException {
        User user = this.userRepository.findByUserId(UUID.fromString(userDeviceMappingDto.getUserId()))
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND + userDeviceMappingDto.getUserId()));
        Device device = this.deviceRepository.findById(UUID.fromString(userDeviceMappingDto.getDeviceId()))
                .orElseThrow(() -> new NoSuchElementException(DEVICE_NOT_FOUND + userDeviceMappingDto.getDeviceId()));
        if (user.getDevices().contains(device)) {
            user.getDevices().remove(device);
            device.setOwner(null);
            this.deviceChangeSender.send(DeviceUtils.formatDeviceChangeToJsonString(this.objectMapper, device));
        }
        else {
            throw new NoSuchElementException(String
                    .format("The user with id: %s does not own the device with id: %s",
                            userDeviceMappingDto.getUserId(),
                            userDeviceMappingDto.getDeviceId()));
        }
        return null;
    }

    public List<DeviceDto> getAllDevicesForUser(String id) {
        User user = this.userRepository.findByUserId(UUID.fromString(id))
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND +id));
        return user.getDevices().stream()
                .map(device -> this.mapper.map(device, DeviceDto.class))
                .toList();

    }
}
