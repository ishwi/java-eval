package com.github.natanbc.javaeval.internal;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class OutputJavaFileObject extends SimpleJavaFileObject {
    private final ByteArrayOutputStream outputStream;
    private final String className;

    public OutputJavaFileObject(String className, Kind kind) {
        super(URI.create("mem:///" + className.replace('.', '/') + kind.extension), kind);
        this.className = className;
        outputStream = new ByteArrayOutputStream();
    }

    @Override
    public OutputStream openOutputStream() {
        return outputStream;
    }

    public byte[] getBytes() {
        return outputStream.toByteArray();
    }

    public String getClassName() {
        return className;
    }
}