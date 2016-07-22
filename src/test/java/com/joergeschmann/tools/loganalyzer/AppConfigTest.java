package com.joergeschmann.tools.loganalyzer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.joergeschmann.tools.loganalyzer.config.ArgumentInfoRegistry;
import com.joergeschmann.tools.loganalyzer.config.ParsedArgument;
import com.joergeschmann.tools.loganalyzer.utils.JsonUtil;

public class AppConfigTest {

    private static ArgumentInfoRegistry argumentInfoRegistry;

    @BeforeClass
    public static void beforeClass() {
        argumentInfoRegistry = new ArgumentInfoRegistry(AppConfig.class.getPackage().getName());
        argumentInfoRegistry.init();
    }

    @Test
    public void fillAppConfig() throws IOException {

        URL configFileUrl = URLClassLoader.getSystemResource("config.json");
        File configFile = new File(configFileUrl.getPath());
        Assert.assertTrue(configFile.exists());

        String configFileContent = new String(Files.readAllBytes(configFile.toPath()));
        ParsedArgument[] parsedArguments = JsonUtil.fromJson(configFileContent, ParsedArgument[].class);

        AppConfig appConfig = new AppConfig(argumentInfoRegistry);
        appConfig.init(parsedArguments);

        Assert.assertTrue(appConfig.isOutputFileDefined());

    }

}
