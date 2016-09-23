package com.joergeschmann.tools.loganalyzer.processing.observer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds the logic to parse a log line and fills a LogEntry with its parts.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
class LogEntryParser {

    public static final Pattern SPACE_PATTERN = Pattern.compile("\\ {1,10}");
    public static final Pattern DATE_PATTERN = Pattern.compile("[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}");
    public static final Pattern TIME_PATTERN = Pattern.compile("[0-9]{2}\\:[0-9]{2}\\:[0-9]{2}\\.[0-9]{3}");
    public static final Pattern SEVERITY_PATTERN = Pattern.compile("[A-Z]{2,10}");
    public static final Pattern THREAD_PATTERN = Pattern.compile("\\[[A-Za-z0-9\\-\\_\\ \\(\\)\\.]{2,200}\\]");
    public static final Pattern COMPONENT_PATTERN = Pattern.compile("[A-Za-z0-9\\ ]{2,200}");
    public static final Pattern META_INFO_DELIMITER_PATTERN = Pattern.compile("\\-");
    public static final Pattern MESSAGE_PATTERN = Pattern.compile(".*");

    private final Pattern logLineStartPattern;

    public LogEntryParser() {

        StringBuilder logLinePatternBuilder = new StringBuilder("^");
        logLinePatternBuilder.append(DATE_PATTERN.pattern());
        logLinePatternBuilder.append(SPACE_PATTERN);
        logLinePatternBuilder.append(TIME_PATTERN);
        logLinePatternBuilder.append(SPACE_PATTERN);
        logLinePatternBuilder.append(SEVERITY_PATTERN);
        logLinePatternBuilder.append(SPACE_PATTERN);
        this.logLineStartPattern = Pattern.compile(logLinePatternBuilder.toString());

    }

    public boolean indicatesNewEntry(final String line) {
        return this.logLineStartPattern.matcher(line).find();
    }

    public LogEntry parse(final String content) {

        final LogEntry entry = new LogEntry();

        String remainingContent = content;

        final String dateString = extractValueAndPrepareForNextExtraction(DATE_PATTERN, remainingContent);
        entry.setDate(dateString);
        remainingContent = prepareForNextExtraction(remainingContent, dateString);

        final String timeString = extractValueAndPrepareForNextExtraction(TIME_PATTERN, remainingContent);
        entry.setTime(timeString);
        remainingContent = prepareForNextExtraction(remainingContent, timeString);

        final String severityString = extractValueAndPrepareForNextExtraction(SEVERITY_PATTERN, remainingContent);
        entry.setSeverity(severityString);
        remainingContent = prepareForNextExtraction(remainingContent, severityString);

        final String threadString = extractValueAndPrepareForNextExtraction(THREAD_PATTERN, remainingContent);
        entry.setThreadName(threadString);
        remainingContent = prepareForNextExtraction(remainingContent, threadString);

        final String componentString = extractValueAndPrepareForNextExtraction(COMPONENT_PATTERN, remainingContent);
        entry.setComponentName(componentString);
        remainingContent = prepareForNextExtraction(remainingContent, componentString);

        final String delimitterString = extractValueAndPrepareForNextExtraction(META_INFO_DELIMITER_PATTERN,
                remainingContent);
        remainingContent = prepareForNextExtraction(remainingContent, delimitterString);

        final String message = extractValueAndPrepareForNextExtraction(MESSAGE_PATTERN, remainingContent);
        entry.appendMessageLine(message);

        return entry;

    }

    private String extractValueAndPrepareForNextExtraction(final Pattern pattern, String line) {

        String result = null;
        final Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            result = matcher.group();
        }

        return result;

    }

    private String prepareForNextExtraction(final String line, final String lastValue) {
        return lastValue == null ? line : line.substring(lastValue.length()).trim();
    }
}
