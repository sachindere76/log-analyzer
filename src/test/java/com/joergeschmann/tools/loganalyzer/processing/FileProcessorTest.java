package com.joergeschmann.tools.loganalyzer.processing;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.io.Files;
import com.joergeschmann.tools.loganalyzer.processing.observer.LogLineObserver;

public class FileProcessorTest {

    @Test
    public void processSmallLogFile() throws IOException {

        URL testFileUrl = URLClassLoader.getSystemResource("logfiles/small.log");

        LineCounterObserver observer = new LineCounterObserver();

        FileProcessor processor = FileProcessorBuilder.createProcessor(testFileUrl.getFile());
        processor.addObserver(observer);

        processor.process();

        File testFile = new File(testFileUrl.getFile());
        int numberOfLines = Files.readLines(testFile, Charset.defaultCharset()).size();
        Assert.assertEquals(numberOfLines, observer.getCount());

    }

    @Test
    public void processLogFileFolder() throws IOException {

        URL testFolderUrl = URLClassLoader.getSystemResource("logfiles");
        LineCounterObserver observer = new LineCounterObserver();

        MultipleFileProcessor processor = (MultipleFileProcessor) FileProcessorBuilder
                .createProcessor(testFolderUrl.getFile());
        processor.addObserver(observer);

        processor.process();

        File testFolder = new File(testFolderUrl.getFile());
        int numberOfLines = 0;
        for (File child : testFolder.listFiles()) {
            numberOfLines += Files.readLines(child, Charset.defaultCharset()).size();
        }

        Assert.assertEquals(numberOfLines, observer.getCount());
        Assert.assertEquals(testFolder.listFiles().length, processor.getProcessedFiles().size());

    }

    class LineCounterObserver implements LogLineObserver {

        private int lineCounter;

        public LineCounterObserver() {

        }

        @Override
        public void notify(String entry) {
            this.lineCounter++;
        }

        @Override
        public void flush() {
            // not necessary
        }

        public int getCount() {
            return this.lineCounter;
        }

    }

}
