package com.joergeschmann.tools.loganalyzer.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.Test;

import com.joergeschmann.tools.loganalyzer.config.parameter.LogFileConfigParameter;
import com.joergeschmann.tools.loganalyzer.filter.TimeRangeFilter;
import com.joergeschmann.tools.loganalyzer.filter.ValueFilter;
import com.joergeschmann.tools.loganalyzer.utils.JsonUtil;

public class ArgumentParserTest {

    @Test
    public void parseConfigFile() throws IOException {

	URL configFileUrl = URLClassLoader.getSystemResource("config.json");
	File configFile = new File(configFileUrl.getPath());
	Assert.assertTrue(configFile.exists());

	String configFileContent = new String(Files.readAllBytes(configFile.toPath()));
	ParsedArgument[] parsedArguments = JsonUtil.fromJson(configFileContent, ParsedArgument[].class);

	Assert.assertEquals("src/test/resources/logfiles/medium.log", parsedArguments[0].getValues().get(0));
	Assert.assertEquals("Class1", parsedArguments[4].getValues().get(1));
	Assert.assertEquals("i", parsedArguments[6].getOptions().get(0));

    }

    @Test
    public void parseSingleValueArgument() {

	String[] arguments = new String[] { "--logFile", "C:\\temp\\test.log" };

	ParsedArgument[] parsedArguments = ArgumentParser.parse(arguments);

	Assert.assertEquals(1, parsedArguments.length);

	ParsedArgument firstArgument = parsedArguments[0];
	Assert.assertTrue(firstArgument.getOptions().isEmpty());
	Assert.assertEquals(LogFileConfigParameter.KEY, firstArgument.getKey());
	Assert.assertEquals(arguments[1], firstArgument.getValues().get(0));

    }

    @Test
    public void parseMultiValueArgument() {

	String key = "--valueFilter";
	String firstValue = "thread";
	String secondValue = "Test_Thread-30";
	String[] arguments = new String[] { key, firstValue, secondValue };

	ParsedArgument[] parsedArguments = ArgumentParser.parse(arguments);

	Assert.assertEquals(1, parsedArguments.length);

	ParsedArgument firstArgument = parsedArguments[0];
	Assert.assertTrue(firstArgument.getOptions().isEmpty());
	Assert.assertEquals(ValueFilter.KEY, firstArgument.getKey());
	Assert.assertEquals(firstValue, firstArgument.getValues().get(0));
	Assert.assertEquals(secondValue, firstArgument.getValues().get(1));

    }

    @Test
    public void parseSingleAndMultipleValueArgumentsWithOptions() {

	String[] arguments = new String[] { // no linebreak
	        "--logFile", "C:\\temp\\test.log", // no linebreak
	        "--timeRangeFilter", "-nv", "16:40:43", "16:40:45", // no
	                                                            // linebreak
	        "--valueFilter", "-n", "thread", "Test-Thread-1" };

	ParsedArgument[] parsedArguments = ArgumentParser.parse(arguments);

	Assert.assertEquals(3, parsedArguments.length);

	ParsedArgument firstArgument = parsedArguments[0];
	Assert.assertTrue(firstArgument.getOptions().isEmpty());
	Assert.assertEquals(LogFileConfigParameter.KEY, firstArgument.getKey());
	Assert.assertEquals(arguments[1], firstArgument.getValues().get(0));

	ParsedArgument secondArgument = parsedArguments[1];
	Assert.assertEquals(2, secondArgument.getOptions().size());
	Assert.assertEquals(secondArgument.getOptions().get(0), "n");
	Assert.assertEquals(secondArgument.getOptions().get(1), "v");
	Assert.assertEquals(TimeRangeFilter.KEY, secondArgument.getKey());
	Assert.assertEquals(2, secondArgument.getValues().size());
	Assert.assertEquals(arguments[4], secondArgument.getValues().get(0));
	Assert.assertEquals(arguments[5], secondArgument.getValues().get(1));

	ParsedArgument thirdArgument = parsedArguments[2];
	Assert.assertEquals(1, thirdArgument.getOptions().size());
	Assert.assertEquals(thirdArgument.getOptions().get(0), "n");
	Assert.assertEquals(ValueFilter.KEY, thirdArgument.getKey());
	Assert.assertEquals(2, thirdArgument.getValues().size());
	Assert.assertEquals(arguments[8], thirdArgument.getValues().get(0));
	Assert.assertEquals(arguments[9], thirdArgument.getValues().get(1));

    }
}
