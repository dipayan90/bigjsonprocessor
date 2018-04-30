package com.kajjoy.bigjsonprocessor;

import com.kajjoy.bigjsonprocessor.service.GzipStreamer;
import com.kajjoy.bigjsonprocessor.service.JSONProcessor;

public class Application {

    public static void main(String[] args){
        String gzipFileLocation = System.getProperty("gzipfilelocation");
        boolean status =  JSONProcessor.getInstance().readGzipStreamAndWriteToFile(GzipStreamer.getInstance().getGzipStream(gzipFileLocation));
        if(status) {
            System.out.println("Big JSON processed successfully");
        } else {
            System.out.println("JSON processing unsuccessful");
        }
    }
}
