package com.java.spring.app.controller;

import com.java.spring.app.model.Device;
import com.java.spring.app.services.DeviceNativeRepositoryService;
import com.java.spring.app.services.DeviceNativeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequestMapping(value = "nativeDevice", produces = "application/json")
@RestController
public class DeviceNativeController {

    private final DeviceNativeService deviceNativeService;
    private final DeviceNativeRepositoryService deviceNativeRepositoryService;

    public DeviceNativeController(DeviceNativeService deviceNativeService, DeviceNativeRepositoryService deviceNativeRepositoryService) {
        this.deviceNativeService = deviceNativeService;
        this.deviceNativeRepositoryService = deviceNativeRepositoryService;
    }

    @GetMapping
    public String getDevices() {
//        return deviceNativeService.getAllDevices().toString();
        return deviceNativeRepositoryService.getAllDevices().toString();
    }

    @PostMapping
    public void addDevice(@RequestBody Device device) {
        device.setLast_time(LocalDateTime.now());
//        deviceNativeService.addDevice(device);
        deviceNativeRepositoryService.insertQuery(device);
    }

}
