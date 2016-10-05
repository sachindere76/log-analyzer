package com.joergeschmann.tools.loganalyzer.config.modifier;

/**
 * Defines flags that provide more flexibility for arguments.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public enum ConfigModifier {

    INACTIVE("i"), UNIQUE("u");

    private final String key;

    ConfigModifier(final String key) {
	this.key = key;
    }

    public String getKey() {
	return this.key;
    }

}
