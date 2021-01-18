package com.java.spring.app.controller;

import com.java.spring.app.model.Device;
import com.java.spring.app.services.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequestMapping(value = "d", produces = "application/json")
@RestController
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    private String getDevices() {
        return deviceService.getAllDevices().toString();
    }

    @GetMapping(path = "{uuid}")
    private String getDevice(@PathVariable(name = "uuid") UUID uuid) {
        return deviceService.getDevice(uuid).toString();
    }

    @PostMapping
    private void setDevice(@RequestBody Device device) {
        deviceService.addDevice(device);
    }

    @PutMapping
    private void updateDevice(@RequestBody Device device) {
        deviceService.updateDevice(device);
    }

    @PutMapping(path = "{username}")
    private void updateDeviceUser(@RequestBody Device device, @PathVariable(name = "username") String username) {
        deviceService.updateDeviceUser(device, username);
    }

    @DeleteMapping(path = "{uuid}")
    private void deleteDeviceById(@PathVariable("uuid") UUID uuid) {
        deviceService.deleteDeviceByUUID(uuid);
    }

    @DeleteMapping
    private void deleteDevice(@RequestBody Device device) {
        deviceService.deleteDevice(device);
    }

}

