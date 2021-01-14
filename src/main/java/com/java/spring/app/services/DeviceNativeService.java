package com.java.spring.app.services;

import com.java.spring.app.model.Device;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DeviceNativeService {

    private final DeviceNativeRepository deviceNativeRepository;

    public DeviceNativeService(DeviceNativeRepository deviceNativeRepository) {
        this.deviceNativeRepository = deviceNativeRepository;
    }

    @Transactional
    public void addDevice(Device device) {
        deviceNativeRepository.addDevice(device.getLast_time(), device.getModel(), device.getUuid());
    }


}
