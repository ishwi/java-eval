package com.github.natanbc.javaeval;

import com.github.natanbc.javaeval.internal.OutputJavaFileObject;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final List<OutputJavaFileObject> outputFiles;

    protected SimpleFileManager(JavaFileManager fileManager) {
        super(fileManager);
        outputFiles = new ArrayList<>();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        OutputJavaFileObject file = new OutputJavaFileObject(className, kind);
        outputFiles.add(file);
        return file;
    }

    /**
     * List of all output files created by the compiler
     *
     * @return the list of output files
     */
    public List<OutputJavaFileObject> getGeneratedOutputFiles() {
        return outputFiles;
    }
}
