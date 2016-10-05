package com.joergeschmann.tools.loganalyzer.config.parameter;

import com.joergeschmann.tools.loganalyzer.config.ArgumentInfo;

/**
 * Enables to write the result into a file.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
@ArgumentInfo(name = OutputFileParameter.KEY, constructorArguments = {
        String.class }, description = "--outputFile \"/var/tmp/output.txt\"")
public class OutputFileParameter implements ConfigParameter {

    public static final String KEY = "outputFile";

    private final String outputFilePath;

    public OutputFileParameter(final String outputFilePath) {
	this.outputFilePath = outputFilePath;
    }

    @Override
    public String getId() {
	return OutputFileParameter.class.getName();
    }

    @Override
    public String getValue() {
	return this.outputFilePath;
    }

}
