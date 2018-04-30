package com.kajjoy.bigjsonprocessor.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileSystemWriter {

    private FileSystemWriter(){}

    private static FileSystemWriter fileSystemWriter;

    public void writeToFile(String filePath, String data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(data);
        writer.close();
    }

    public static FileSystemWriter getInstance(){
        if(fileSystemWriter == null){
            fileSystemWriter = new FileSystemWriter();
        }
        return fileSystemWriter;
    }

}