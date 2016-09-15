package com.joergeschmann.tools.loganalyzer;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Assert;
import org.junit.Test;

public class AppTest {

    @Test
    public void run() {

        URL logFileUrl = URLClassLoader.getSystemResource("logfiles/small.log");
        File outputFile = new File(System.getProperty("java.io.tmpdir"), "log-analyer-result.txt");
        
        if(outputFile.exists()) {
        	outputFile.delete();
        }
        
        Assert.assertFalse(outputFile.exists());

        String[] arguments = new String[] { // no line break

                "--logFile", logFileUrl.getPath(), // no line break
                "--outputFile", outputFile.getAbsolutePath(), // no line break
                "--exactDateFilter", "2016-06-15", // no line break
                "--timeRangeFilter", "23:58:00", "23:59:00", // no line break
                "--valueFilter", "-n", "component", "Class1", "valueFilter", "-i", "message",
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"

        };

        App app = new App(arguments);
        app.run();
        
        Assert.assertTrue(outputFile.exists());

    }

}
