package com.kajjoy.bigjsonprocessor.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.zip.GZIPInputStream;

public class GzipStreamer {

    private GzipStreamer(){}

    private static GzipStreamer gzipStreamer;

    public GZIPInputStream getGzipStream(String fileLocation){
        InputStream fileIs = null;
        BufferedInputStream bufferedIs = null;
        GZIPInputStream gzipIs =  null;
        try {
            fileIs = Files.newInputStream(Paths.get(fileLocation), new StandardOpenOption[]{StandardOpenOption.READ});
            // Even though GZIPInputStream has a buffer it reads individual bytes
            // when processing the header, better add a buffer in-between
            bufferedIs = new BufferedInputStream(fileIs, 65535);
            gzipIs = new GZIPInputStream(bufferedIs);
        } catch (IOException e) {
            closeSafely(gzipIs);
            closeSafely(bufferedIs);
            closeSafely(fileIs);
            throw new UncheckedIOException(e);
        }
        return gzipIs;
    }

    private static void closeSafely(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static GzipStreamer getInstance(){
        if(gzipStreamer == null){
            gzipStreamer = new GzipStreamer();
        }
        return gzipStreamer;
    }
}