package com.joergeschmann.tools.loganalyzer.output;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joergeschmann.tools.loganalyzer.config.ArgumentInfo;
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

    public RegexExtractedOutputField(String fieldName, String regexPattern) {
	this.fieldName = fieldName;
	this.pattern = Pattern.compile(regexPattern);
    }

    @Override
    public String extractValue(T entry) {
	String fieldValue = entry.getValue(this.fieldName);
	if (fieldValue == null) {
	    return "";
	}
        Matcher matcher = pattern.matcher(fieldValue);
        return matcher.find() ? matcher.group() : "";
    }

}
