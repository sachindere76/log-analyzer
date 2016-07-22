package com.joergeschmann.tools.loganalyzer.filter;

import java.time.LocalTime;

import com.joergeschmann.tools.loganalyzer.config.ArgumentInfo;

/**
 * Checks a log entry for a time range.
 * 
 * @author joerg.eschmann@gmail.com
 *
 * @param <T>
 */
@ArgumentInfo(name = TimeRangeFilter.KEY, constructorArguments = { String.class,
        String.class }, description = "--timeRangeFilter \"12:00:00\" \"13:00:00\"")
public class TimeRangeFilter<T extends FilterableTime> extends AbstractFilter<T> {

    public static final String KEY = "timeRangeFilter";

    private final int startTimeSecondsOfDay;
    private final int endTimeSecondsOfDay;

    public TimeRangeFilter(final String startTime, final String endTime) {
        this.startTimeSecondsOfDay = LocalTime.parse(startTime).toSecondOfDay();
        this.endTimeSecondsOfDay = LocalTime.parse(endTime).toSecondOfDay();
    }

    @Override
    public String getId() {
        return this.getClass().getName();
    }

    @Override
    public boolean isRelevant(final T entry) {
        int testTimeSeconds = LocalTime.parse(entry.getTime()).toSecondOfDay();
        boolean isRelevant = startTimeSecondsOfDay < testTimeSeconds && testTimeSeconds < endTimeSecondsOfDay;
        return applyOptions(isRelevant);
    }
}
