package com.java.spring.app.model;

import javax.persistence.*;

@Entity
@Table(name = "uploadedFiles")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String contentType;
    @Lob
    private byte[] fileData;
    private int size;

    public File() {
    }

    public File(String fileName, String contentType, byte[] fileData) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileData = fileData;
        this.size = this.fileData.length;
    }

    public int getSize() {
        return size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String fileExtension) {
        this.contentType = fileExtension;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        int si = size;
        int x = 1;
        String name = "B";
        while (si / 1024 > 0) {
            si = si / 1024;
            x++;
        }
        switch (x) {

            case 1:
                name = "B";
                break;
            case 2:
                name = "kB";
                break;
            case 3:
                name = "MB";
                break;
            case 4:
                name = "GB";
                break;
        }
        return "{" +
                "\"fileName\": \"" + fileName + "\"" +
                ", \"contentType\": \"" + contentType + "\"" +
                ", \"size\": \"" + si + " " + name + "\"" +
                "}";
    }
}
