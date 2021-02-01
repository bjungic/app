package com.java.spring.app.controller;

import com.java.spring.app.model.User;
import com.java.spring.app.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RequestMapping(value = "u", produces = "application/json")
@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    private String getUsers(HttpServletRequest httpRequest) {
        logger.info(java.time.LocalDateTime.now() + "     IP: " + httpRequest.getRemoteAddr());
//        System.out.println(java.time.LocalDateTime.now() + "     IP: " + httpRequest.getRemoteAddr());
        return userService.getAllUsers().toString();
    }

    @GetMapping(path = "{username}")
    private String getUserByUsername(@PathVariable(value = "username") String username) {
        return userService.getUser(username).toString();
    }

    @PostMapping(path = "{uuid}")
    private void setUserFromDevice(@RequestBody User user, @PathVariable("uuid") UUID uuid) {
        try {
            userService.addUserFromDevice(user, uuid);

        } catch (Exception e) {
            logger.error(String.valueOf(e.getStackTrace()));
//            System.out.println(e.getMessage());
        }
    }

    @PostMapping
    private void setUser(@RequestBody User user) {
        try {
            userService.addUser(user);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
//            System.out.println(e.getMessage());
        }
    }

    @PutMapping
    private void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @PutMapping(path = "{uuid}")
    private void updateUserDevice(@RequestBody User user, @PathVariable("uuid") UUID uuid) {
        userService.updateUserDevice(user, uuid);
    }

    @DeleteMapping(path = "{username}")
    private void deleteUserById(@PathVariable("username") String username) {
        userService.deleteUserByUsername(username);
    }

    @DeleteMapping
    private void deleteUser(@RequestBody User user) {
        userService.deleteUser(user);
    }

}
