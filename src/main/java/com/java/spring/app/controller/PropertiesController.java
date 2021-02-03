package com.java.spring.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;

@Controller
public class PropertiesController {

    Logger logger = LoggerFactory.getLogger(PropertiesController.class);

    @RequestMapping(value = "/properties")
    public String properties(Model model) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        classLoader.getResource("application.properties");
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

    @PostMapping("/saveproperties")
    public String saveProperties(@RequestParam String prop, Model model) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String properties = String.valueOf(classLoader.getResource("application.properties"));
        logger.error(properties.substring(6));
        FileOutputStream outputStream = new FileOutputStream(properties.substring(6));
        byte[] strToBytes = prop.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
        return "redirect:/properties";
    }
}
