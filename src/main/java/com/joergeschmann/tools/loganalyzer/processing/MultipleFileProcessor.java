package com.joergeschmann.tools.loganalyzer.processing;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes a directory containing multiple files. Each file is processed be a
 * SingleFileProcessor.
 * 
 * @author joerg.eschmann@gmail.com
 *
 */
class MultipleFileProcessor extends AbstractFileProcessor {

    private final File logFolder;
    private List<String> processedFiles;

    MultipleFileProcessor(final File logFolder) {
	this.logFolder = logFolder;
	this.processedFiles = new ArrayList<String>();
    }

    @Override
    public void process() {

	processChildren(logFolder.listFiles());

    }

    void processChildren(final File[] children) {

	// Process all files in the same folder first, then look for recursive
	// children.
	final List<File> recursiveFolders = new ArrayList<>();

	for (File child : children) {
	    if (child.isDirectory()) {
		recursiveFolders.add(child);
	    }
	    else {
		processFile(child);
	    }
	}

	for (File file : recursiveFolders) {
	    processChildren(file.listFiles());
	}
    }

    void processFile(final File logFile) {

	this.processedFiles.add(logFile.getAbsolutePath());

	final SingleFileProcessor processor = new SingleFileProcessor(logFile);
	processor.addObservers(getObservers());
	processor.process();

    }

    List<String> getProcessedFiles() {
	return this.processedFiles;
    }
}
