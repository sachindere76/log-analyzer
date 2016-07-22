package com.joergeschmann.tools.loganalyzer.processing.observer;

import org.slf4j.Logger;

import com.joergeschmann.tools.loganalyzer.filter.FilterProcessor;

/**
 * Handles multi-line log entries and composes a LogEntry instance of the log lines that belong together. If the
 * LogEntry is relevant, the concrete implementation outputs its value.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
abstract class AbstractLogEntryObserver implements LogLineObserver {

    private final FilterProcessor<LogEntry> filterProcessor;
    private final LogEntryParser logEntryParser;
    private LogEntry logEntry;

    public AbstractLogEntryObserver(final FilterProcessor<LogEntry> filterProcessor,
            final LogEntryParser logEntryParser) {
        this.filterProcessor = filterProcessor;
        this.logEntryParser = logEntryParser;
        this.logEntry = new LogEntry();
    }

    abstract void writeLogEntry(LogEntry entry);

    abstract Logger getLogger();

    @Override
    public void notify(String logLine) {

        if (logEntryParser.indicatesNewEntry(logLine)) {

            flush();

            logEntry = logEntryParser.parse(logLine);
        } else {
            logEntry.appendMessageLine(logLine);
        }

    }

    @Override
    public void flush() {
        if (logEntry.hasContent() && filterProcessor.isRelevant(logEntry)) {
            writeLogEntry(logEntry);
        }
    }

}
