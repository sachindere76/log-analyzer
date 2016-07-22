package com.joergeschmann.tools.loganalyzer.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Applies all registered filters to a log entry and return true, if the entry is relevant.
 * 
 * @author joerg.eschmann@gmail.com
 *
 * @param <T>
 */
public class FilterProcessor<T> {

    private final List<Filter<T>> filterList;

    public FilterProcessor() {
        this.filterList = new ArrayList<>();
    }

    public void addFilter(final Filter<T> filter) {
        this.filterList.add(filter);
    }

    public void addFilters(final List<Filter<T>> list) {
        this.filterList.addAll(list);
    }

    public boolean isRelevant(final T entry) {
        for (Filter<T> filter : this.filterList) {
            if (!filter.isRelevant(entry)) {
                return false;
            }
        }
        return true;
    }

}
