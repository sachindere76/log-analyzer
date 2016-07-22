package com.joergeschmann.tools.loganalyzer;

import java.io.File;
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

    public static void main(String[] arguments) {

        App app = new App(arguments);
        app.run();

    }

    private final String[] arguments;
    private final Gson gson = new Gson();

    public App(final String[] arguments) {
        this.arguments = arguments;
    }

    public void run() {

        final ParsedArgument[] parsedArguments = parseConfigFileOrArguments();

        final ArgumentInfoRegistry arugmentInfoRegistry = new ArgumentInfoRegistry(
                this.getClass().getPackage().getName());
        arugmentInfoRegistry.init();

        final AppConfig appConfig = new AppConfig(arugmentInfoRegistry);
        appConfig.init(parsedArguments);

        final FileProcessor processor = FileProcessorBuilder.createProcessor(appConfig.getLogFilePath());
        processor.addObserver(LogEntryObserverBuilder.createObserver(appConfig));

        LOGGER.info("================== Log excerpt start ====================");
        processor.process();
        LOGGER.info("================== Log excerpt end ====================");

    }

    private ParsedArgument[] parseConfigFileOrArguments() {

        final File configFile = new File("config.json");
        LOGGER.debug("Config file path: {}", configFile.getAbsolutePath());
        if (!configFile.exists()) {
            return ArgumentParser.parse(this.arguments);
        }

        try {
            final String configFileContent = new String(Files.readAllBytes(configFile.toPath()));
            return this.gson.fromJson(configFileContent, ParsedArgument[].class);
        } catch (Exception exc) {
            throw new RuntimeException("Could nor parse config file");
        }

    }

}
