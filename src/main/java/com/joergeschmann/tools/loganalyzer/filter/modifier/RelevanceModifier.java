package com.joergeschmann.tools.loganalyzer.filter.modifier;

/**
 * Provides additional functionality for a Filter.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public interface RelevanceModifier {

    /**
     * Modifies the relevance result.
     * 
     * @param result
     * @return
     */
    boolean modify(boolean result);

}
