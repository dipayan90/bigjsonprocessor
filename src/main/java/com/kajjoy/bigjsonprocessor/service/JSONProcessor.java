package com.kajjoy.bigjsonprocessor.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class JSONProcessor {

    private JSONProcessor(){}

    private static JSONProcessor jsonProcessor;
    private final int MAX_RECORD_COUNT_IN_BUCKET = 6;


    public boolean readGzipStreamAndWriteToFile(GZIPInputStream gzipInputStream){
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(gzipInputStream));
            Gson gson = new GsonBuilder().create();

            // Read file in stream mode
            reader.beginArray();
            int recordCount = 0;
            StringBuilder sb = null;
            String demarcator = "";
            while (reader.hasNext()) {
                // Bucketing policy, make buckets of size X and buffer write or do something.
                if(recordCount % MAX_RECORD_COUNT_IN_BUCKET == 0 ){
                    if(sb != null &&  0 != sb.length() ){
                        FileSystemWriter.getInstance().writeToFile(String.valueOf(recordCount),sb.toString());
                    }
                    sb = new StringBuilder();
                    demarcator = "";
                }

                JsonObject record = gson.fromJson(reader,JsonObject.class);
                sb.append(demarcator);
                demarcator = "\n";
                sb.append(record.toString());
                recordCount++;
            }
            // Process Items That are lesser than bucket size
            if(sb != null && 0 != sb.length() ){
                FileSystemWriter.getInstance().writeToFile(String.valueOf(recordCount),sb.toString());
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static JSONProcessor getInstance(){
        if(jsonProcessor == null){
            jsonProcessor = new JSONProcessor();
        }
        return jsonProcessor;
    }
}
