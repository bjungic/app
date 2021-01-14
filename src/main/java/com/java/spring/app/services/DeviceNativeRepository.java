package com.java.spring.app.services;

import com.java.spring.app.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DeviceNativeRepository extends JpaRepository<Device, Long> {

    @Query(value = "select * from devices", nativeQuery = true)
    List<Device> getAllDevices();

    @Query(value = "select * from devices where uuid = ?1", nativeQuery = true)
    Device getDeviceByUUID(String uuid);

    @Modifying
    @Query(value = "insert into devices (last_time, model, uuid) values (?1, ?2, ?3)", nativeQuery = true)
    void addDevice(LocalDateTime last_time, String model, UUID uuid);

}
