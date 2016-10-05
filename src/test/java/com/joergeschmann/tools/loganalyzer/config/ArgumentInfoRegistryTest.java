package com.joergeschmann.tools.loganalyzer.config;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joergeschmann.tools.loganalyzer.App;
import com.joergeschmann.tools.loganalyzer.filter.ExactDateFilter;
import com.joergeschmann.tools.loganalyzer.filter.TimeRangeFilter;
import com.joergeschmann.tools.loganalyzer.filter.ValueFilter;
import com.joergeschmann.tools.loganalyzer.processing.observer.LogEntry;

public class ArgumentInfoRegistryTest {

    private final Logger LOGGER = LoggerFactory.getLogger(ArgumentInfoRegistryTest.class);

    private ArgumentInfoRegistry registry;

    @Before
    public void before() {

	registry = new ArgumentInfoRegistry(App.class.getPackage().getName());
	registry.init();

    }

    @Test
    public void registerFilters() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {

	for (String description : registry.getArgumentDescriptions()) {
	    LOGGER.debug(description);
	}

	Assert.assertTrue(registry.isArgumentInfoAvailable(ExactDateFilter.KEY));
	Assert.assertTrue(registry.isArgumentInfoAvailable(TimeRangeFilter.KEY));
	Assert.assertTrue(registry.isArgumentInfoAvailable(ValueFilter.KEY));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void instantiateValueFilter() throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

	ArgumentInfo valueFilterArgumentInfos = registry.getArgumentInfo(ValueFilter.KEY);
	Class<?> definingClass = registry.getDefiningClass(ValueFilter.KEY);

	ValueFilter<LogEntry> valueFilterInstance = (ValueFilter<LogEntry>) definingClass
	        .getDeclaredConstructor(valueFilterArgumentInfos.constructorArguments())
	        .newInstance(new Object[] { "", "" });

	Assert.assertNotNull(valueFilterInstance);

    }

}
