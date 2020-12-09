package com.java.spring.app.services;

import com.java.spring.app.model.Device;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Long> {

}
