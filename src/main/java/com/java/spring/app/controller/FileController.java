package com.java.spring.app.controller;

import com.java.spring.app.model.File;
import com.java.spring.app.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("file")
@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile[] files) throws IOException {
        for (MultipartFile f : files) {
            fileService.save(f);
        }
    }

    @GetMapping
    public String getAllFiles() {
        return fileService.getAllFiles().toString();
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> getFileByID(@PathVariable(value = "fileName") String fileName) {
        try {
            File file = fileService.getFileByID(fileName).orElse(null);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file.getFileData());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
