package com.joergeschmann.tools.loganalyzer.processing.observer;

import com.joergeschmann.tools.loganalyzer.filter.FilterableDate;
import com.joergeschmann.tools.loganalyzer.filter.FilterableTime;
import com.joergeschmann.tools.loganalyzer.filter.FilterableValue;

/**
 * Represents a log entry that can consist of multiple lines. This class is
 * highly depends on the LogEntryParser.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public class LogEntry implements FilterableDate, FilterableTime, FilterableValue {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private String date;
    private String time;
    private String severity;
    private String threadName;
    private String componentName;
    private StringBuilder message;

    public LogEntry() {
	this.message = new StringBuilder(0);
    }

    @Override
    public String getValue(String key) {

	if ("thread".equalsIgnoreCase(key)) {
	    return this.threadName;
	}
	else if ("component".equalsIgnoreCase(key)) {
	    return this.componentName;
	}
	else if ("message".equalsIgnoreCase(key)) {
	    return this.message.toString();
	}
	else {
	    return "";
	}
    }

    @Override
    public String getDate() {

	return date;
    }

    @Override
    public String getTime() {

	return time;
    }

    public void appendMessageLine(final String content) {

	if (hasContent()) {
	    this.message.append(LINE_SEPARATOR);
	}
	this.message.append(content);
    }

    public boolean hasContent() {

	return this.message.length() > 0;
    }

    public String getContent() {

	return this.message.toString();
    }

    public String serialize() {
	return String.format("%s %s %s %s %s %s", this.date, this.time, this.severity, this.threadName,
	        this.componentName, this.message.toString());
    }

    public void setDate(String date) {

	this.date = date;
    }

    public void setTime(String time) {

	this.time = time;
    }

    public String getSeverity() {

	return severity;
    }

    public void setSeverity(String severity) {

	this.severity = severity;
    }

    public String getThreadName() {

	return threadName;
    }

    public void setThreadName(String threadName) {

	this.threadName = threadName;
    }

    public String getComponentName() {

	return componentName;
    }

    public void setComponentName(String componentName) {

	this.componentName = componentName;
    }

    public StringBuilder getMessage() {

	return message;
    }

}
