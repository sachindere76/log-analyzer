package com.joergeschmann.tools.loganalyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joergeschmann.tools.loganalyzer.config.ArgumentInfo;
import com.joergeschmann.tools.loganalyzer.config.ArgumentInfoRegistry;
import com.joergeschmann.tools.loganalyzer.config.ParsedArgument;
import com.joergeschmann.tools.loganalyzer.config.modifier.ConfigModifier;
import com.joergeschmann.tools.loganalyzer.config.parameter.ConfigParameter;
import com.joergeschmann.tools.loganalyzer.config.parameter.LogFileConfigParameter;
import com.joergeschmann.tools.loganalyzer.config.parameter.OutputFileParameter;
import com.joergeschmann.tools.loganalyzer.filter.Filter;
import com.joergeschmann.tools.loganalyzer.filter.modifier.RelevanceModifier;
import com.joergeschmann.tools.loganalyzer.filter.modifier.RelevanceModifierBuilder;
import com.joergeschmann.tools.loganalyzer.processing.observer.LogEntry;

/**
 * Holds all necessary information to run the analyzer. Classes annotated with ArgumentInfo are automatically available
 * by the ArgumentInfoRegistry.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public class AppConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    private final ArgumentInfoRegistry argumentInfoRegistry;
    private Map<String, String> configValues;
    private final List<Filter<LogEntry>> filterList;

    public AppConfig(final ArgumentInfoRegistry argumentInfoRegistry) {
        this.argumentInfoRegistry = argumentInfoRegistry;
        this.configValues = new HashMap<>();
        this.filterList = new ArrayList<>();
    }

    /**
     * Currently, there are two types of arguments. On the one hand are filters and on the other hand are config
     * parameters. A Filter is used to determine whether a log line is relevant and should be displayed. A
     * ConfigParameter is used to set values that impact the file reading part like which log file is read.
     * 
     * @param parsedArguments
     */
    public void init(final ParsedArgument[] parsedArguments) {

        for (ParsedArgument argument : parsedArguments) {

            if (argument.getOptions().contains(ConfigModifier.Inactive.getKey())) {
                LOGGER.debug("Skipping inactive argument: {}", argument.toString());
                continue;
            }

            final Class<?> definingClass = this.argumentInfoRegistry.getDefiningClass(argument.getKey());

            if (Filter.class.isAssignableFrom(definingClass)) {
                addFilter(definingClass, argument);
            } else if (ConfigParameter.class.isAssignableFrom(definingClass)) {
                addConfigValue(definingClass, argument);
            }

        }

    }

    public String getLogFilePath() {
        return configValues.get(LogFileConfigParameter.KEY);
    }

    public boolean isOutputFileDefined() {
        return configValues.containsKey(OutputFileParameter.KEY);
    }

    public String getOutputFilePath() {
        return configValues.get(OutputFileParameter.KEY);
    }

    public List<Filter<LogEntry>> getFilterList() {
        return this.filterList;
    }

    @SuppressWarnings("unchecked")
    private void addFilter(final Class<?> definingClass, final ParsedArgument parsedArgument) {

        final ArgumentInfo argumentInfos = this.argumentInfoRegistry.getArgumentInfo(parsedArgument.getKey());

        try {

            final Filter<LogEntry> newFilter = (Filter<LogEntry>) definingClass
                    .getDeclaredConstructor(argumentInfos.constructorArguments())
                    .newInstance(parsedArgument.getValues().toArray());

            for (String option : parsedArgument.getOptions()) {
                final Optional<RelevanceModifier> modifier = RelevanceModifierBuilder.create(option);
                if (modifier.isPresent()) {
                    newFilter.addRelevanceModifier(modifier.get());
                }
            }

            this.filterList.add(newFilter);

        } catch (Exception exc) {
            LOGGER.warn("Could not create filter: {} -> {} ", definingClass.getName(), exc.getMessage());
        }

    }

    private void addConfigValue(final Class<?> definingClass, final ParsedArgument parsedArgument) {

        final ArgumentInfo argumentInfos = this.argumentInfoRegistry.getArgumentInfo(parsedArgument.getKey());

        try {

            final ConfigParameter newParameter = (ConfigParameter) definingClass
                    .getDeclaredConstructor(argumentInfos.constructorArguments())
                    .newInstance(parsedArgument.getValues().toArray());

            this.configValues.put(parsedArgument.getKey(), newParameter.getValue());

        } catch (Exception exc) {
            LOGGER.warn("Could not create config parameter: {} -> {} ", definingClass.getName(), exc.getMessage());
        }

    }
}
