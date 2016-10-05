package com.joergeschmann.tools.loganalyzer.processing.observer;

import java.util.List;

import org.slf4j.Logger;

import com.joergeschmann.tools.loganalyzer.filter.FilterProcessor;
import com.joergeschmann.tools.loganalyzer.output.OutputField;

/**
 * Handles multi-line log entries and composes a LogEntry instance of the log
 * lines that belong together. If the LogEntry is relevant, the concrete
 * implementation outputs its value.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
abstract class AbstractLogEntryObserver implements LogLineObserver {

    private final FilterProcessor<LogEntry> filterProcessor;
    private final LogEntryParser logEntryParser;
    private LogEntry logEntry;
    private final List<OutputField<LogEntry>> outputFields;

    public AbstractLogEntryObserver(final FilterProcessor<LogEntry> filterProcessor,
            final LogEntryParser logEntryParser, final List<OutputField<LogEntry>> outputFields) {
	this.filterProcessor = filterProcessor;
	this.logEntryParser = logEntryParser;
	this.logEntry = new LogEntry();
	this.outputFields = outputFields;
    }

    abstract void writeLogEntry(String entry);

    abstract Logger getLogger();

    @Override
    public void notify(String logLine) {

	if (logEntryParser.indicatesNewEntry(logLine)) {

	    flush();

	    logEntry = logEntryParser.parse(logLine);
	}
	else {
	    logEntry.appendMessageLine(logLine);
	}

    }

    @Override
    public void flush() {
	if (!logEntry.hasContent() || !filterProcessor.isRelevant(logEntry)) {
	    return;
	}
	else if (this.outputFields.isEmpty()) {
	    writeFullEntry(logEntry);
	}
	else {
	    writeMatchesOnly(logEntry);
	}
    }

    private void writeFullEntry(LogEntry entry) {
	writeLogEntry(entry.serialize());
    }

    private void writeMatchesOnly(LogEntry entry) {
	StringBuilder builder = new StringBuilder();
	for (OutputField<LogEntry> field : this.outputFields) {
	    builder.append(field.extractValue(entry));
	}
	if (builder.length() > 0) {
	    writeLogEntry(builder.toString());
	}
    }

}
