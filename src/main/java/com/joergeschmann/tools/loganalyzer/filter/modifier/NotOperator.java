package com.joergeschmann.tools.loganalyzer.filter.modifier;

/**
 * Inverts the previous result with a not operation.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public class NotOperator implements RelevanceModifier {

    public static final String KEY = "n";

    @Override
    public boolean modify(boolean result) {
        return !result;
    }

}
