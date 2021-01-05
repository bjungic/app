package com.java.spring.app.services;

import com.java.spring.app.model.Device;
import com.java.spring.app.model.User;
import com.java.spring.app.security.PasswordConfig;
import com.java.spring.app.security.Role;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final PasswordConfig passwordConfig;

    private final DeviceService deviceService;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(PasswordConfig passwordConfig, DeviceService deviceService, UserRepository userRepository, RoleService roleService) {
        this.passwordConfig = passwordConfig;
        this.deviceService = deviceService;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public void addUserFromDevice(User user, UUID uuid) {
        Optional<Device> dev = deviceService.getDevice(uuid);
        if (getUser(user.getUsername()) == null) {
            user.getDevices().add(dev.get());
            userRepository.save(user);
        } else {
            User u = getUser(user.getUsername());
            u.getDevices().add(dev.get());
            userRepository.save(u);
        }
    }

    public void addUser(User user) {
        if (getUser(user.getUsername()) == null) {
            user.setPassword(passwordConfig.passwordEncoder().encode(user.getPassword()));
            Set<Role> roles = new HashSet<>();
            for (Role r : user.getRoles()) {
                if (roleService.getRoleByName(r.getName().toUpperCase()) != null)
                    roles.add(roleService.getRoleByName(r.getName().toUpperCase()));
                else {
                    if (roles.isEmpty()) {
                        System.out.println("Unknown ROLE: " + r.getName() + ". Set it to \"USER\"");
                        if ((roleService.getRoleByName("USER") != null)) {
                            roles.add(roleService.getRoleByName("USER"));
                        } else {
                            roles.add(new Role("USER"));
                        }
                    }
                }
            }
            user.setRoles(roles);
            userRepository.save(user);
        }
        System.out.println("Dodan user: " + user.getUsername());
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
        System.out.println(java.time.LocalDateTime.now() + "   Ne postoji user: " + username);
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
                for (Device device : deviceService.getAllDevices()) {
                    if (device.getUuid().equals(uuid)) {
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

    public void removeAllDevices(User user) {
        user.getDevices().removeAll(user.getDevices());
    }
}
