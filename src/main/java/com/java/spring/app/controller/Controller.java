package com.java.spring.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RequestMapping("/")
@RestController
public class Controller {

    @GetMapping
    public String index() {
        Instant time = Instant.now();
        return "<h1>bjungic " + time + "</h1>";
    }
}
