package com.joergeschmann.tools.loganalyzer.config.parameter;

import com.joergeschmann.tools.loganalyzer.config.ArgumentInfo;

/**
 * Provides a path to a log file or log folder that will be processed.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
@ArgumentInfo(name = LogFileConfigParameter.KEY, constructorArguments = {
        String.class }, description = "--logFile \"/var/tmp/test.log\"")
public class LogFileConfigParameter implements ConfigParameter {

    public static final String KEY = "logFile";

    private final String filePath;

    public LogFileConfigParameter(final String filePath) {
	this.filePath = filePath;
    }

    @Override
    public String getId() {
	return LogFileConfigParameter.class.getName();
    }

    @Override
    public String getValue() {
	return filePath;
    }

}
