package com.joergeschmann.tools.loganalyzer.filter.modifier;

import java.util.Optional;

/**
 * Creates a new instance of a RelevaneModifier.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public final class RelevanceModifierBuilder {

    private RelevanceModifierBuilder() {}

    public static Optional<RelevanceModifier> create(final String key) {
        switch (key) {
            case NotOperator.KEY:
                return Optional.of(new NotOperator());
            default:
                return Optional.empty();
        }
    }

}
