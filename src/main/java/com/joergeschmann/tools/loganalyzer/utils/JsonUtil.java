package com.joergeschmann.tools.loganalyzer.utils;

import com.google.gson.Gson;

/**
 * Provides functionality to convert to and from JSON.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public final class JsonUtil {

    private static final Gson GSON = new Gson();

    private JsonUtil() {

    }

    /**
     * Converts the object into a json string with the Gson library.
     * 
     * @param object
     * @return
     */
    public static String toJson(final Object object) {
	return GSON.toJson(object);
    }

    /**
     * Parses the json string with the Gson library.
     * 
     * @param json
     * @param desiredClass
     * @return
     */
    public static <T> T fromJson(final String json, final Class<T> desiredClass) {
	return GSON.fromJson(json, desiredClass);
    }

}
