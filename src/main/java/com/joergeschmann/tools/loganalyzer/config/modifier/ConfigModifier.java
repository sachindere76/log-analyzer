package com.joergeschmann.tools.loganalyzer.config.modifier;

/**
 * Provides the flag -i that makes an argument inactive.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public enum ConfigModifier {

    Inactive("i");

    private final String key;

    ConfigModifier(final String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

}
