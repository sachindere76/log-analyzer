package com.joergeschmann.tools.loganalyzer.output;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joergeschmann.tools.loganalyzer.config.ArgumentInfo;
import com.joergeschmann.tools.loganalyzer.config.modifier.ConfigModifier;
import com.joergeschmann.tools.loganalyzer.filter.FilterableValue;

/**
 * Extracts the a value out of a field that matches to a pattern.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
@ArgumentInfo(name = RegexExtractedOutputField.KEY, constructorArguments = { String.class,
        String.class }, description = "--regexExtractedOutputField \"message\" \"test\"")
public class RegexExtractedOutputField<T extends FilterableValue> implements OutputField<T> {

    public static final String KEY = "regexExtractedOutputField";

    private final String fieldName;
    private final Pattern pattern;
    private final Set<String> processedValues;
    private boolean uniqueValues;

    public RegexExtractedOutputField(String fieldName, String regexPattern) {
	this.fieldName = fieldName;
	this.pattern = Pattern.compile(regexPattern);
	this.processedValues = new HashSet<>();
	this.uniqueValues = false;
    }

    @Override
    public void addOption(String option) {
	if (ConfigModifier.UNIQUE.getKey().equals(option)) {
	    this.uniqueValues = true;
	}
    }

    @Override
    public String extractValue(T entry) {
	String fieldValue = entry.getValue(this.fieldName);
	if (fieldValue == null) {
	    return "";
	}

	Matcher matcher = pattern.matcher(fieldValue);
	if (!matcher.find()) {
	    return "";
	}

	if (this.uniqueValues) {
	    return this.processedValues.add(matcher.group()) ? matcher.group() : "";
	}
	else {
	    return matcher.group();
	}
    }

}
