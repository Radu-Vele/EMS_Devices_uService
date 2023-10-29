package com.devicesus.services;

import com.devicesus.dto.DeviceDto;
import com.devicesus.dto.UserDeviceMappingDto;
import com.devicesus.entities.Device;
import com.devicesus.entities.User;
import com.devicesus.repositories.DeviceRepository;
import com.devicesus.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

import static com.devicesus.constants.ExceptionMessages.DEVICE_NOT_FOUND;
import static com.devicesus.constants.ExceptionMessages.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public UUID register(String id) {
        return this.userRepository.save(new User(UUID.fromString(id)))
                .getId();
    }

    public Void delete(String userId) {
        User user = this.userRepository.findByUserId(UUID.fromString(userId))
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND + userId));
        this.userRepository.delete(user);
        return null;
    }

    public Void addDeviceToUser(UserDeviceMappingDto userDeviceMappingDto) {
        User user = this.userRepository.findByUserId(UUID.fromString(userDeviceMappingDto.getUserId()))
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND + userDeviceMappingDto.getUserId()));
        Device device = this.deviceRepository.findById(UUID.fromString(userDeviceMappingDto.getDeviceId()))
                .orElseThrow(() -> new NoSuchElementException(DEVICE_NOT_FOUND + userDeviceMappingDto.getDeviceId()));
        user.getDevices().add(device);
        return null;
    }

    public Void removeDeviceFromUser(UserDeviceMappingDto userDeviceMappingDto) {
        User user = this.userRepository.findByUserId(UUID.fromString(userDeviceMappingDto.getUserId()))
                .orElseThrow(() -> new NoSuchElementException(USER_NOT_FOUND + userDeviceMappingDto.getUserId()));
        Device device = this.deviceRepository.findById(UUID.fromString(userDeviceMappingDto.getDeviceId()))
                .orElseThrow(() -> new NoSuchElementException(DEVICE_NOT_FOUND + userDeviceMappingDto.getDeviceId()));
        if (user.getDevices().contains(device)) {
            user.getDevices().remove(device);
        }
        else {
            throw new NoSuchElementException(String
                    .format("The user with id: %s does not own the device with id: %s",
                            userDeviceMappingDto.getUserId(),
                            userDeviceMappingDto.getDeviceId()));
        }
        return null;
    }
}
