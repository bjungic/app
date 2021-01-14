package com.java.spring.app.controller;

import com.java.spring.app.model.Device;
import com.java.spring.app.services.DeviceNativeRepositoryService;
import com.java.spring.app.services.DeviceNativeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<Device> getDevices() {
        return deviceNativeService.getAllDevices();
    }

    @PostMapping
    public void addDevice(@RequestBody Device device) {
        device.setLast_time(LocalDateTime.now());
//        deviceNativeService.addDevice(device);
        deviceNativeRepositoryService.insertQuery(device);
    }

}
