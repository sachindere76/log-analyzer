package com.joergeschmann.tools.loganalyzer.processing.observer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joergeschmann.tools.loganalyzer.filter.FilterProcessor;
import com.joergeschmann.tools.loganalyzer.output.OutputField;

/**
 * Appends the log entries to the console.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public class LogEntryConsoleAppenderObserver extends AbstractLogEntryObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogEntryConsoleAppenderObserver.class);

    public LogEntryConsoleAppenderObserver(FilterProcessor<LogEntry> filterProcessor, LogEntryParser logEntryParser,
            final List<OutputField<LogEntry>> outputFields) {
        super(filterProcessor, logEntryParser, outputFields);
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    @Override
    void writeLogEntry(String entry) {
        System.out.println(entry);
    }

}
