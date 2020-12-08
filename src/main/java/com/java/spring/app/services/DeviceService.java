package com.java.spring.app.services;

import com.java.spring.app.model.Device;
import com.java.spring.app.model.User;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public DeviceService(DeviceRepository deviceRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    public void addDevice(Device device) {
        if (deviceRepository.findAll().iterator().hasNext()) {
            if (!deviceRepository.findAll().iterator().next().getUuid().equals(device.getUuid())) {
                device.setLast_time(java.time.LocalDateTime.now());
                deviceRepository.save(device);
            }
        } else {
            device.setLast_time(java.time.LocalDateTime.now());
            deviceRepository.save(device);
        }

    }

    private void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    public Optional<Device> getDevice(UUID uuid) {
        Iterable<Device> result = deviceRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            Device d = (Device) iter.next();
            if (uuid.equals(d.getUuid())) {
                return deviceRepository.findById(d.getId());
            }
        }
        return null;
    }

    public Iterable<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public void updateDevice(Device device) {
        Iterable<Device> result = deviceRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            Device d = (Device) iter.next();
            if (device.getUuid().equals(d.getUuid())) {
                device.setId(d.getId());
                device.setLast_time(java.time.LocalDateTime.now());
                deviceRepository.save(device);
            }
        }
    }

    public void updateDeviceUser(Device device, String username) {
        Iterable<Device> result = deviceRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            Device d = (Device) iter.next();
            if (device.getUuid().equals(d.getUuid())) {
                device.setId(d.getId());
                for (User user : userRepository.findAll()){
                    if (user.getUsername().equals(username)){
                        user.getDevices().add(d);
                    }
                }
                d.setLast_time(java.time.LocalDateTime.now());
                deviceRepository.save(d);
            }
        }
    }

    public void deleteDevice(Device device) {
        Iterable<Device> result = deviceRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            Device d = (Device) iter.next();
            if (device.getUuid().equals(d.getUuid())) {
                deviceRepository.deleteById(d.getId());
            }
        }
    }

    public void deleteDeviceByUUID(UUID uuid) {
        Iterable<Device> result = deviceRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            Device d = (Device) iter.next();
            if (d.getUuid().equals(uuid)) {
                for (User u : d.getUsers()) {
                    u.removeDevice(d);
                }
                deviceRepository.deleteById(d.getId());
            }
        }
    }
}
