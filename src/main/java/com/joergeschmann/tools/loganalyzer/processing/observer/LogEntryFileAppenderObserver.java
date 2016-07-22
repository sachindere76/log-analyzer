package com.joergeschmann.tools.loganalyzer.processing.observer;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joergeschmann.tools.loganalyzer.filter.FilterProcessor;
import com.joergeschmann.tools.loganalyzer.utils.FileUtil;

/**
 * Writes the log entries into a file.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public class LogEntryFileAppenderObserver extends AbstractLogEntryObserver {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogEntryFileAppenderObserver.class);

    private final File outputFile;

    public LogEntryFileAppenderObserver(final FilterProcessor<LogEntry> filterProcessor,
            final LogEntryParser logEntryParser, final File outputFile) {

        super(filterProcessor, logEntryParser);
        this.outputFile = outputFile;
        FileUtil.ensureIsEmptyFile(this.outputFile);

    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    @Override
    void writeLogEntry(LogEntry entry) {

        LOGGER.info(entry.serialize());
        FileUtil.appendLineToFile(this.outputFile, entry.serialize());

    }

}
