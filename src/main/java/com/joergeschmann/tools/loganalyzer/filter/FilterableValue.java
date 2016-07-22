package com.joergeschmann.tools.loganalyzer.filter;

/**
 * Provides a value that can be filtered.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public interface FilterableValue extends Filterable {

    /**
     * Returns the value that needs to be filtered.
     * 
     * @param key
     * @return
     */
    String getValue(String key);

}
