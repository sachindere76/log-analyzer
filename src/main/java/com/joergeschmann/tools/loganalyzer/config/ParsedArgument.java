package com.joergeschmann.tools.loganalyzer.config;

import java.util.ArrayList;
import java.util.List;

import com.joergeschmann.tools.loganalyzer.utils.JsonUtil;

/**
 * Holds all the information about an argument.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public class ParsedArgument {

    private final String key;
    private final List<String> options;
    private final List<String> values;

    public ParsedArgument(final String key) {
	this.key = key;
	this.options = new ArrayList<>();
	this.values = new ArrayList<>();
    }

    public String getKey() {
	return this.key;
    }

    public void addOption(final String option) {
	this.options.add(option);
    }

    public List<String> getOptions() {
	return this.options;
    }

    public void addValue(final String value) {
	this.values.add(value);
    }

    public List<String> getValues() {
	return this.values;
    }

    @Override
    public String toString() {
	return JsonUtil.toJson(this);
    }

}
