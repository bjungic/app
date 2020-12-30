package com.java.spring.app.services;

import com.java.spring.app.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    public void save(MultipartFile multipartFile) throws IOException {
        String fileName = org.springframework.util.StringUtils.cleanPath(multipartFile.getOriginalFilename());
        File file = new File(fileName, multipartFile.getContentType(), multipartFile.getBytes());
        fileRepository.save(file);
    }

    public Optional<File> getFileByID (Long id){
        return fileRepository.findById(id);
    }

    public Iterable<File> getAllFiles (){
        return fileRepository.findAll();
    }
}
