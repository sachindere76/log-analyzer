package com.joergeschmann.tools.loganalyzer;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.joergeschmann.tools.loganalyzer.config.ArgumentInfoRegistry;
import com.joergeschmann.tools.loganalyzer.config.ArgumentParser;
import com.joergeschmann.tools.loganalyzer.config.ParsedArgument;
import com.joergeschmann.tools.loganalyzer.processing.FileProcessor;
import com.joergeschmann.tools.loganalyzer.processing.FileProcessorBuilder;
import com.joergeschmann.tools.loganalyzer.processing.observer.LogEntryObserverBuilder;

/**
 * Entrance point to start the log analyzer.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private static final String CONFIG_FILE_NAME = "config.json";

    public static void main(String[] arguments) {

	App app = new App(arguments);
	app.run();

    }

    private final String[] arguments;
    private final Gson gson = new Gson();

    public App(final String[] arguments) {
	this.arguments = arguments == null ? new String[0] : arguments;
    }

    public void run() {

	final ParsedArgument[] parsedArguments = parseConfigFileOrArguments();

	final ArgumentInfoRegistry argumentInfoRegistry = new ArgumentInfoRegistry(
	        this.getClass().getPackage().getName());
	argumentInfoRegistry.init();

	final AppConfig appConfig = new AppConfig(argumentInfoRegistry);
	appConfig.init(parsedArguments);

	final FileProcessor processor = FileProcessorBuilder.createProcessor(appConfig.getLogFilePath());
	processor.addObserver(LogEntryObserverBuilder.createObserver(appConfig));

	LOGGER.info("================== Log excerpt start ====================");
	processor.process();
	LOGGER.info("================== Log excerpt end ====================");

    }

    private ParsedArgument[] parseConfigFileOrArguments() {

	final File configFile = getConfigFile();
	if (this.arguments.length > 0 || !configFile.exists()) {
	    return ArgumentParser.parse(this.arguments);
	}

	try {
	    final String configFileContent = new String(Files.readAllBytes(configFile.toPath()));
	    return this.gson.fromJson(configFileContent, ParsedArgument[].class);
	}
	catch (Exception exc) {
	    throw new RuntimeException("Could not parse config file");
	}

    }

    private File getConfigFile() {
	try {
	    URL configFileUrl = URLClassLoader.getSystemResource(CONFIG_FILE_NAME);
	    File configFile = new File(configFileUrl.toURI());
	    LOGGER.debug("Config file path: {}", configFile.getAbsolutePath());
	    return configFile;
	}
	catch (URISyntaxException exc) {
	    LOGGER.warn("Config file not found {}", CONFIG_FILE_NAME);
	    return new File(CONFIG_FILE_NAME);
	}
    }

}
