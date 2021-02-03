package com.java.spring.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
public class PropertiesController {

    Logger logger = LoggerFactory.getLogger(PropertiesController.class);

    @RequestMapping(value = "/prop")
    public String properties(Model model) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.properties");
        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";

        while ((line = br.readLine()) != null) {
            if (line.length() < 0) break;

            resultStringBuilder.append(line).append("\n");
        }
        model.addAttribute("properties", resultStringBuilder.toString());
        return "properties";
    }
}
