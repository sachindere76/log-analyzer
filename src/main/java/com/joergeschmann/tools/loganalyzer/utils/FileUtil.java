package com.joergeschmann.tools.loganalyzer.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import com.google.common.io.Files;

/**
 * Provides functionality for working with files.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
public final class FileUtil {

    private static final Charset UTF8_Charset = Charset.forName("UTF-8");

    private FileUtil() {

    }

    /**
     * Appends the line with a line break and the UTF-8 charset.
     * 
     * @param file
     * @param value
     */
    public static void appendLineToFile(final File file, final String value) {
	try {
	    Files.append(value + "\r\n", file, UTF8_Charset);
	}
	catch (IOException exc) {
	    throw new RuntimeException("Could not append to file", exc);
	}
    }

    /**
     * Deletes the file if it exists and creates a new one.
     * 
     * @param file
     */
    public static void ensureIsEmptyFile(final File file) {
	if (file.exists()) {
	    file.delete();
	}
	try {
	    file.createNewFile();
	}
	catch (IOException exc) {
	    throw new RuntimeException("Could not create new file", exc);
	}
    }

}
