package com.java.spring.app.controller;

import com.java.spring.app.model.User;
import com.java.spring.app.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RequestMapping("u")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    private String getUsers(HttpServletRequest httpRequest) {
        System.out.println(java.time.LocalDateTime.now() + "     IP: " + httpRequest.getRemoteAddr());
        return userService.getAllUsers().toString();
    }

    @GetMapping(path = "{id}")
    private String getUserByUsername(@PathVariable(value = "id") String username) {
        return userService.getUser(username).toString();
    }

    @PostMapping(path = "{id}")
    private void setUserFromDevice(@RequestBody User user, @PathVariable("id") UUID uuid) {
        try {
            userService.addUserFromDevice(user, uuid);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping
    private void setUser(@RequestBody User user) {
        try {
            userService.addUser(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PutMapping
    private void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @PutMapping(path = "{id}")
    private void updateUserDevice(@RequestBody User user, @PathVariable("id") UUID uuid) {
        userService.updateUserDevice(user, uuid);
    }

    @DeleteMapping(path = "{id}")
    private void deleteUserById(@PathVariable("id") String username) {
        userService.deleteUserByUsername(username);
    }

    @DeleteMapping
    private void deleteUser(@RequestBody User user) {
        userService.deleteUser(user);
    }

}
