package com.kajjoy.bigjsonprocessor;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class ApplicationTest {

    @Test
    public void testApplication(){
        System.setProperty("gzipfilelocation","src/test/resources/gzipfile.json.gz");
        Application.main(new String[]{});
        Assert.assertTrue(new File("6").exists());
        Assert.assertTrue(new File("100").exists());
    }
}
