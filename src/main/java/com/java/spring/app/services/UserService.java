package com.java.spring.app.services;

import com.java.spring.app.model.Device;
import com.java.spring.app.model.User;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final DeviceService deviceService;
    private final UserRepository userRepository;

    public UserService(DeviceService deviceService, UserRepository userRepository) {
        this.deviceService = deviceService;
        this.userRepository = userRepository;
    }

    public void addUserFromDevice(User user, UUID uuid) {
        Optional<Device> dev = deviceService.getDevice(uuid);
        if (getUser(user.getUsername()) == null) {
            user.getDevices().add(dev.get());
            System.out.println(user);
            userRepository.save(user);
        } else {
            User u = getUser(user.getUsername());
            u.getDevices().add(dev.get());
            userRepository.save(u);
        }
    }

    public void addUser(User user) {
        if (getUser(user.getUsername()) == null) {
            userRepository.save(user);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUser(String username) {
        Iterable<User> result = userRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            User u = (User) iter.next();
            if (username.equals(u.getUsername())) {
                return u;
            }
        }
        return null;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUser(User user) {
        Iterable<User> result = userRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            User u = (User) iter.next();
            if (user.getUsername().equals(u.getUsername())) {
                user.setId(u.getId());
                user.getDevices().removeAll(user.getDevices());
                userRepository.save(user);
            }
        }
    }

    public void updateUserDevice(User user, UUID uuid) {
        Iterable<User> result = userRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            User u = (User) iter.next();
            if (user.getUsername().equals(u.getUsername())) {
                user.setId(u.getId());
                for (Device device : deviceService.getAllDevices()){
                    if (device.getUuid().equals(uuid)){
                        u.getDevices().add(device);
                    }
                }
                userRepository.save(u);
            }
        }
    }

    public void deleteUser(User user) {
        Iterable<User> result = userRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            User u = (User) iter.next();
            if (user.getUsername().equals(u.getUsername())) {
                userRepository.deleteById(u.getId());
            }
        }
    }

    public void deleteUserByUsername(String username) {
        Iterable<User> result = userRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            User u = (User) iter.next();
            if (u.getUsername().equals(username)) {
                userRepository.deleteById(u.getId());
            }
        }
    }

    public void removeAllDevices(User user){
        user.getDevices().removeAll(user.getDevices());
    }
}
