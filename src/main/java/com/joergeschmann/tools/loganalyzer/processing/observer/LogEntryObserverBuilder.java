package com.joergeschmann.tools.loganalyzer.processing.observer;

import java.io.File;

import com.joergeschmann.tools.loganalyzer.AppConfig;
import com.joergeschmann.tools.loganalyzer.filter.FilterProcessor;

/**
 * Builder for LogEntryObservers.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public final class LogEntryObserverBuilder {

    private LogEntryObserverBuilder() {

    }

    /**
     * Creates either a file appender or a console appender.
     * 
     * @param appConfig
     * @return
     */
    public static AbstractLogEntryObserver createObserver(final AppConfig appConfig) {

        final LogEntryParser logEntryParser = new LogEntryParser();
        final FilterProcessor<LogEntry> filterProcessor = new FilterProcessor<>();
        filterProcessor.addFilters(appConfig.getFilterList());

        if (appConfig.isOutputFileDefined()) {
            final File outputFile = new File(appConfig.getOutputFilePath());
            return new LogEntryFileAppenderObserver(filterProcessor, logEntryParser, outputFile);
        } else {
            return new LogEntryConsoleAppenderObserver(filterProcessor, logEntryParser);
        }

    }

}
