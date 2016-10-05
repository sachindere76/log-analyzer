package com.joergeschmann.tools.loganalyzer.config.parameter;

/**
 * Provides an id and a value for the AppConfig. To add a new ConfigParameter,
 * create a new class, extend ConfigParameter and annotate it with ArgumentInfo.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public interface ConfigParameter {

    String getId();

    String getValue();

}
