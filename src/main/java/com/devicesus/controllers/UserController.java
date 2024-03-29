package com.devicesus.controllers;

import com.devicesus.dto.DeviceDto;
import com.devicesus.dto.UserDeviceMappingDto;
import com.devicesus.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/registerUserId")
    public ResponseEntity<UUID> registerUserId(@RequestParam String id) {
        return new ResponseEntity<>(this.userService.register(id), HttpStatus.CREATED);
    }

    @DeleteMapping("/removeUserAndMapping")
    public ResponseEntity<Void> removeUserAndMapping(@RequestParam String id) throws JsonProcessingException {
        return new ResponseEntity<>(this.userService.delete(id), HttpStatus.OK);
    }

    @PutMapping("/addDeviceToUser")
    public ResponseEntity<Void> addDeviceToUser(@RequestBody UserDeviceMappingDto userDeviceMappingDto) throws JsonProcessingException {
        return new ResponseEntity<>(this.userService.addDeviceToUser(userDeviceMappingDto), HttpStatus.OK);
    }

    @PutMapping("/removeDeviceFromUser")
    public ResponseEntity<Void> removeDeviceFromUser(@RequestBody UserDeviceMappingDto userDeviceMappingDto) throws JsonProcessingException {
        return new ResponseEntity<>(this.userService.removeDeviceFromUser(userDeviceMappingDto), HttpStatus.OK);
    }

    @GetMapping("/getAllDevices")
    public ResponseEntity<List<DeviceDto>> getAllDevicesForUser(@RequestParam String id) { // TODO: extract username from token / offer a new endpoint with getMyDevices
        return new ResponseEntity<>(this.userService.getAllDevicesForUser(id), HttpStatus.OK);
    }
}
