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
        File file = new File(fileName, multipartFile.getContentType(), multipartFile.getBytes(), getUsername());
        fileRepository.save(file);
    }

    public Optional<File> getFileByID(String fileName) {
        Iterable<File> result = fileRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            File f = (File) iter.next();
            if (f.getFileName().equals(fileName) && f.getOwner().equals(getUsername())) {
                return fileRepository.findById(f.getId());
            }
        }
        return null;
    }

    public Iterable<File> getAllFiles() {
        Iterable<File> result = fileRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            File f = (File) iter.next();
            if (!f.getOwner().equals(getUsername())) {
                iter.remove();
            }
        }
        return result;
    }

    private String getUsername() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return username;
    }

    public void deleteFile(String fileName) {
        Iterable<File> result = fileRepository.findAll();
        Iterator iter = result.iterator();
        while (iter.hasNext()) {
            File f = (File) iter.next();
            if (f.getFileName().equals(fileName) && f.getOwner().equals(getUsername())) {
                System.out.println(f.getOwner() + "           " + getUsername());
                fileRepository.deleteById(f.getId());
            }
        }
    }
}
