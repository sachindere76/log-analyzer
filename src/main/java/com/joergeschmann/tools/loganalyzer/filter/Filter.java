package com.joergeschmann.tools.loganalyzer.filter;

import com.joergeschmann.tools.loganalyzer.filter.modifier.RelevanceModifier;

/**
 * Provides the functionality to filter a log file and decide which entries are relevant to further processing. If you
 * want to add a new Filter, create a new Class, extend Filter and add the ArgumentInfo annotation.
 * 
 * @author joerg.eschmann@gmail.com
 *
 * @param <T>
 */
public interface Filter<T> {

    /**
     * @return An unique id like the class name of the filter.
     */
    String getId();

    /**
     * Adds a modifier to affect the relevance of the result.
     * 
     * @param modifier
     */
    void addRelevanceModifier(RelevanceModifier modifier);

    /**
     * Checks if the log entry is relevant.
     * 
     * @param value
     * @return
     */
    boolean isRelevant(T value);

}
