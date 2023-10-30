package com.devicesus.controllers;

import com.devicesus.dto.DeviceCreatedDto;
import com.devicesus.dto.DeviceDto;
import com.devicesus.services.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

// TODO: add security (verify JWT from front end)

@Controller
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/create")
    public ResponseEntity<UUID> createDevice(@RequestBody @Valid DeviceCreatedDto deviceCreatedDto) {
        return new ResponseEntity<>(deviceService.create(deviceCreatedDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(@RequestParam String id) {
        return new ResponseEntity<>(deviceService.deleteById(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<DeviceDto>> getAll() {
        return new ResponseEntity<>(deviceService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<Void> editDevice(@RequestBody DeviceDto deviceNewData) {
        return new ResponseEntity<>(deviceService.edit(deviceNewData), HttpStatus.OK);
    }
}
