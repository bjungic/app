package com.java.spring.app.controller;

import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@RequestMapping("/")
@RestController
public class Controller {

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withLocale(Locale.forLanguageTag("hr-HR"))
            .withZone(ZoneId.systemDefault());

    @GetMapping
    public String index() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("/home/bjungic/aplikacija/app.log");
        String log = IOUtils.toString(fileInputStream);
        Instant time = Instant.now();
        return "<h1>bjungic <br><br>" + dateTimeFormatter.format(time) + "</h1><br><br>" + log;
    }
}
