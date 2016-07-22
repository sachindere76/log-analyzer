package com.joergeschmann.tools.loganalyzer.processing;

import java.io.File;

/**
 * Creates a new instance of a FileProcessor.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public final class FileProcessorBuilder {

    private FileProcessorBuilder() {

    }

    /**
     * Creates either SingleFileProcessor or a MuliFileProcessor instance.
     * 
     * @param logFilePath
     * @return
     */
    public static FileProcessor createProcessor(final String logFilePath) {

        if (logFilePath == null) {
            throw new RuntimeException("Please provide a valid log file path: " + logFilePath);
        }

        final File logFile = new File(logFilePath);
        if (!logFile.exists()) {
            throw new RuntimeException("Log file path does not exist: " + logFilePath);
        }

        if (logFile.isFile()) {
            return new SingleFileProcessor(logFile);
        } else if (logFile.isDirectory()) {
            return new MultipleFileProcessor(logFile);
        } else {
            throw new RuntimeException("Neither file nor directory: " + logFilePath);
        }

    }

}
