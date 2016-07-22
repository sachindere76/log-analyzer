package com.joergeschmann.tools.loganalyzer.filter;

import java.util.ArrayList;
import java.util.List;

import com.joergeschmann.tools.loganalyzer.filter.modifier.RelevanceModifier;

/**
 * Implements the common filter functionality.
 * 
 * @author joerg.eschmann@gmail.com
 *
 * @param <T>
 */
abstract class AbstractFilter<T extends Filterable> implements Filter<T> {

    private List<RelevanceModifier> options;

    public AbstractFilter() {
        this.options = new ArrayList<>();
    }

    public List<RelevanceModifier> getOptions() {
        return options;
    }

    @Override
    public void addRelevanceModifier(RelevanceModifier option) {
        this.options.add(option);
    }

    boolean applyOptions(boolean isRelevant) {
        boolean result = isRelevant;
        for (RelevanceModifier option : this.options) {
            result = option.modify(result);
        }
        return result;
    }

}
