package com.joergeschmann.tools.loganalyzer.processing;

import java.util.List;

import com.joergeschmann.tools.loganalyzer.processing.observer.LogLineObserver;

/**
 * Provides the functionality to consume a log line.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public interface FileProcessor {

    /**
     * Registers an observer that is notified whenever a line is read.
     * 
     * @param observer
     */
    void addObserver(final LogLineObserver observer);

    /**
     * Registers observers that are notified whenever a line is read.
     * 
     * @param observers
     */
    void addObservers(final List<LogLineObserver> observers);

    /**
     * Reads the file line by line and notifies all registered observers.
     */
    public void process();

}
