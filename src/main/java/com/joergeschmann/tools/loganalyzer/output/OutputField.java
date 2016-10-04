package com.joergeschmann.tools.loganalyzer.output;

import com.joergeschmann.tools.loganalyzer.filter.FilterableValue;

/**
 * Provides the logic to extract only the value that are needed.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public interface OutputField<T extends FilterableValue> {
	
	void addOption(String value);

    String extractValue(T entry);

}
