package com.joergeschmann.tools.loganalyzer.processing;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Processes a single file by reading line by line.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
class SingleFileProcessor extends AbstractFileProcessor {

    private final File file;

    SingleFileProcessor(final File logFile) {
	this.file = logFile;
    }

    @Override
    public void process() {

	FileReader fileReader = null;
	BufferedReader bufferedReader = null;

	try {
	    fileReader = new FileReader(file);
	    bufferedReader = new BufferedReader(fileReader);
	    String currentLine = null;

	    while ((currentLine = bufferedReader.readLine()) != null) {
		notifyObservers(currentLine);
	    }

	}
	catch (IOException exc) {
	    throw new RuntimeException("Could not read file content", exc);
	}
	finally {
	    close(bufferedReader);
	    close(fileReader);
	}

	// Ensure the last log entry is also written if relevant
	flushObservers();

    }

    private void close(final Closeable instance) {

	if (instance == null) {
	    return;
	}

	try {
	    instance.close();
	}
	catch (Exception exc) {
	    // do not bother
	}

    }
}
