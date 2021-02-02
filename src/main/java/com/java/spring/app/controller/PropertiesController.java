package com.java.spring.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
public class PropertiesController {

    Logger logger = LoggerFactory.getLogger(PropertiesController.class);

    @GetMapping("/properties")
    public String properties(Model model) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            resultStringBuilder.append(line).append("\n");
        }
        model.addAttribute("properties", resultStringBuilder.toString());
        return "properties";
    }
}
