package com.joergeschmann.tools.loganalyzer.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joergeschmann.tools.loganalyzer.utils.JsonUtil;

/**
 * Processes the arguments of the main method and creates an array of ParsedArgument objects.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public final class ArgumentParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArgumentParser.class);

    private static final String NEW_ARGUMENT_INDICATOR = "--";
    private static final String OPTION_INDICATOR = "-";

    private ArgumentParser() {}

    public static ParsedArgument[] parse(final String[] arguments) {

        final List<ParsedArgument> parsedArguments = new ArrayList<>();
        int currentIndex = -1;

        LOGGER.debug("Paring arguments: {}", JsonUtil.toJson(arguments));

        for (String argument : arguments) {

            if (argument.indexOf(NEW_ARGUMENT_INDICATOR) == 0) {

                // New Argument starts;
                currentIndex++;
                parsedArguments.add(new ParsedArgument(argument.substring(2)));

            } else if (argument.indexOf(OPTION_INDICATOR) == 0) {

                if (currentIndex == -1) {
                    throw new RuntimeException("Invalid parameter composition");
                }

                int argumentLength = argument.length();

                // new option(s)
                if (argumentLength < 2) {
                    // a valid option need at least 2 characters like -i
                } else if (argumentLength == 2) {
                    // single option
                    parsedArguments.get(currentIndex).addOption(argument.substring(1));
                } else {
                    // multiple options like -ii
                    for (int index = 1; index < argumentLength; index++) {
                        parsedArguments.get(currentIndex).addOption(argument.substring(index, index + 1));
                    }
                }

            } else {
                if (currentIndex == -1) {
                    throw new RuntimeException("Invalid parameter composition");
                }

                // new value
                parsedArguments.get(currentIndex).addValue(argument);
            }

        }

        LOGGER.debug("Parsed arguments: {}", JsonUtil.toJson(parsedArguments));

        return parsedArguments.toArray(new ParsedArgument[parsedArguments.size()]);

    }
}
