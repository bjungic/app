package com.java.spring.app.services;

import com.java.spring.app.model.Device;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class DeviceNativeRepositoryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertQuery(Device device) {
        entityManager.createNativeQuery("insert into devices (last_time, model, uuid) values (?,?,?)")
                .setParameter(1, device.getLast_time())
                .setParameter(2, device.getModel())
                .setParameter(3, device.getUuid())
                .executeUpdate();
    }
}
