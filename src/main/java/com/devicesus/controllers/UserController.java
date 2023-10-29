package com.devicesus.controllers;

import com.devicesus.dto.UserDeviceMappingDto;
import com.devicesus.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registerUserId")
    public ResponseEntity<UUID> registerUserId(@RequestParam String id) {
        return new ResponseEntity<>(this.userService.register(id), HttpStatus.CREATED);
    }

    @DeleteMapping("/removeUserAndMapping")
    public ResponseEntity<Void> removeUserAndMapping(@RequestParam String id) {
        return new ResponseEntity<>(this.userService.delete(id), HttpStatus.OK);
    }

    @PutMapping("/addDeviceToUser")
    public ResponseEntity<Void> addDeviceToUser(@RequestBody UserDeviceMappingDto userDeviceMappingDto) {
        return new ResponseEntity<>(this.userService.addDeviceToUser(userDeviceMappingDto), HttpStatus.OK);
    }
}
