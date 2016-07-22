package com.joergeschmann.tools.loganalyzer.processing.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joergeschmann.tools.loganalyzer.filter.FilterProcessor;

/**
 * Appends the log entries to the console.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public class LogEntryConsoleAppenderObserver extends AbstractLogEntryObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogEntryConsoleAppenderObserver.class);

    public LogEntryConsoleAppenderObserver(FilterProcessor<LogEntry> filterProcessor, LogEntryParser logEntryParser) {
        super(filterProcessor, logEntryParser);
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    @Override
    void writeLogEntry(LogEntry entry) {
        System.out.println(entry.serialize());
    }

}
