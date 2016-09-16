package com.joergeschmann.tools.loganalyzer;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import org.junit.Assert;
import org.junit.Test;

public class AppTest {

    @Test
    public void run() {

	URL logFileUrl = URLClassLoader.getSystemResource("logfiles/medium.log");
	File outputFile = new File(System.getProperty("java.io.tmpdir"), "log-analyer-result.txt");

	if (outputFile.exists()) {
	    outputFile.delete();
	}

	Assert.assertFalse(outputFile.exists());
	
	//@formatter:off

	String[] arguments = new String[] { 

	        "--logFile", logFileUrl.getPath(), 
	        "--outputFile", outputFile.getAbsolutePath(), 
	        "--exactDateFilter", "2016-06-15", 
	        "--timeRangeFilter", "23:58:00", "23:59:00", 
	        "--regexExtractedOutputField", "message", "instanceId\\=[A-Z]*",
	        "--valueFilter", "-n", "component", "Class1", "valueFilter", "-i", "message",
	        "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"

	};
	
	//@formatter:on

	App app = new App(arguments);
	app.run();

	Assert.assertTrue(outputFile.exists());

    }

}
