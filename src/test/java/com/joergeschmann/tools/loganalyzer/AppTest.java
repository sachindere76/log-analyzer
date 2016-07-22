package com.joergeschmann.tools.loganalyzer;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Test;

public class AppTest {

    @Test
    public void run() {

        URL logFileUrl = URLClassLoader.getSystemResource("logfiles/small.log");

        String[] arguments = new String[] { // no line break

                "--logFile", logFileUrl.getPath(), // no line break
                "--outputFile", "/tmp/result.txt", // no line break
                "--exactDateFilter", "2016-06-15", // no line break
                "--timeRangeFilter", "23:58:00", "23:59:00", // no line break
                "--valueFilter", "-n", "component", "Class1", "valueFilter", "-i", "message",
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"

        };

        App app = new App(arguments);
        app.run();

    }

}
