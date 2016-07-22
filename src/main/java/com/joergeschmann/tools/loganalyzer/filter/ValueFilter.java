package com.joergeschmann.tools.loganalyzer.filter;

import java.util.regex.Pattern;

import com.joergeschmann.tools.loganalyzer.config.ArgumentInfo;

/**
 * Checks if a log entry filed contains a specific value.
 * 
 * @author joerg.eschmann@gmail.com
 *
 * @param <T>
 */
@ArgumentInfo(name = ValueFilter.KEY, constructorArguments = { String.class,
        String.class }, description = "--valueFilter \"message\" \"test\"]")
public class ValueFilter<T extends FilterableValue> extends AbstractFilter<T> {

    public static final String KEY = "valueFilter";

    private final Pattern valuePattern;
    private String key;

    public ValueFilter(final String key, final String valuePattern) {
        this.valuePattern = Pattern.compile(valuePattern);
        this.key = key;
    }

    @Override
    public String getId() {
        return this.getClass().getName();
    }

    @Override
    public boolean isRelevant(final T value) {

        final String text = value.getValue(this.key);
        boolean isRelevant = text != null && this.valuePattern.matcher(text).find();
        return applyOptions(isRelevant);

    }
}
