package com.java.spring.app.controller;

import com.java.spring.app.model.File;
import com.java.spring.app.services.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RequestMapping("file")
@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.save(file);
    }

    @GetMapping
    public Iterable<File> getAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping(path = "{fileName}")
    public Optional<File> getFileByID(@PathVariable (value = "fileName") String fileName){
        System.out.println(fileName);
        return fileService.getFileByID(fileName);
    }
}
