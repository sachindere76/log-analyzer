package com.joergeschmann.tools.loganalyzer.processing.observer;

/**
 * Provides the logic to consume a line read by a FileProcessor.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public interface LogLineObserver {

    /**
     * Notifies the observer with the new log line.
     * 
     * @param entry
     */
    void notify(String entry);

    /**
     * Flushes the remaining log lines.
     */
    void flush();

}
