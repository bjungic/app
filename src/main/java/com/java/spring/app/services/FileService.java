package com.java.spring.app.services;

import com.java.spring.app.model.File;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public void save(MultipartFile multipartFile) throws IOException {
        String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        File file = new File(fileName, multipartFile.getContentType(), multipartFile.getBytes(), username);
        fileRepository.save(file);
    }

    public Optional<File> getFileByID(String fileName) {
        Iterable<File> result = fileRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            File f = (File) iter.next();
            if (f.getFileName().equals(fileName)) {
                return fileRepository.findById(f.getId());
            }
        }
        return null;
    }

    public Iterable<File> getAllFiles() {
        return fileRepository.findAll();
    }
}
