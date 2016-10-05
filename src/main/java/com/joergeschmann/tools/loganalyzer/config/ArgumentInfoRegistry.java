package com.joergeschmann.tools.loganalyzer.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registers and provides all classes that are annotated with ArgumentInfo.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public class ArgumentInfoRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArgumentInfoRegistry.class);

    private final String packageName;
    private final Map<String, Class<?>> argumentClassesMap;
    private final Map<String, ArgumentInfo> argumentInfoMap;

    public ArgumentInfoRegistry(final String packageName) {
	this.packageName = packageName;
	this.argumentClassesMap = new HashMap<>();
	this.argumentInfoMap = new HashMap<>();
    }

    /**
     * Scans all classes of the defined package and registers all ArgumentInfo
     * annotations.
     */
    public void init() {

	final Reflections reflections = new Reflections(this.packageName);
	final Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(ArgumentInfo.class, true);

	for (Class<?> annotatedClass : annotatedClasses) {
	    LOGGER.debug("Registering: {}", annotatedClass.getName());
	    final ArgumentInfo argumentInfos = annotatedClass.getAnnotation(ArgumentInfo.class);
	    this.argumentClassesMap.put(argumentInfos.name(), annotatedClass);
	    this.argumentInfoMap.put(argumentInfos.name(), argumentInfos);
	}

    }

    public boolean isArgumentInfoAvailable(final String key) {
	return this.argumentInfoMap.containsKey(key);
    }

    public ArgumentInfo getArgumentInfo(final String key) {
	return this.argumentInfoMap.get(key);
    }

    public Class<?> getDefiningClass(final String key) {
	return this.argumentClassesMap.get(key);
    }

    public List<String> getArgumentDescriptions() {

	final List<String> descriptions = new ArrayList<>();
	final Set<Entry<String, ArgumentInfo>> entrySet = this.argumentInfoMap.entrySet();

	for (Entry<String, ArgumentInfo> entry : entrySet) {
	    descriptions.add(entry.getValue().description());
	}

	return descriptions;

    }

}
