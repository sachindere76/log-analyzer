package com.joergeschmann.tools.loganalyzer.processing;

import java.util.ArrayList;
import java.util.List;

import com.joergeschmann.tools.loganalyzer.processing.observer.LogLineObserver;

/**
 * Holds the common file processor logic.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
abstract class AbstractFileProcessor implements FileProcessor {

    private List<LogLineObserver> observers;

    AbstractFileProcessor() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(LogLineObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void addObservers(List<LogLineObserver> observers) {
        this.observers.addAll(observers);
    }

    List<LogLineObserver> getObservers() {
        return this.observers;
    }

    void notifyObservers(final String line) {
        for (LogLineObserver observer : observers) {
            observer.notify(line);
        }
    }

    void flushObservers() {
        for (LogLineObserver observer : observers) {
            observer.flush();
        }
    }

}
