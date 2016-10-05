package com.joergeschmann.tools.loganalyzer.filter;

import java.time.LocalDate;

import com.joergeschmann.tools.loganalyzer.config.ArgumentInfo;

/**
 * Checks if the entry has a specific date.
 * 
 * @author joerg.eschmann@gmail.com
 *
 * @param <T>
 */
@ArgumentInfo(name = ExactDateFilter.KEY, constructorArguments = {
        String.class }, description = "--exactDateFilter \"2016-06-15\"")
public class ExactDateFilter<T extends FilterableDate> extends AbstractFilter<T> {

    public static final String KEY = "exactDateFilter";

    private final LocalDate exactDate;

    public ExactDateFilter(final String date) {
	this.exactDate = LocalDate.parse(date);
    }

    @Override
    public String getId() {
	return this.getClass().getName();
    }

    @Override
    public boolean isRelevant(final T entry) {

	final LocalDate date = LocalDate.parse(entry.getDate());
	boolean isRelevant = this.exactDate.isEqual(date);
	return applyOptions(isRelevant);

    }

}
