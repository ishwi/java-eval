package com.github.natanbc.javaeval.internal;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class StringJavaFileObject extends SimpleJavaFileObject {
    private final String sourceCode;

    public StringJavaFileObject(String className, String sourceCode) {
        super(URI.create("string:///" + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension), Kind.SOURCE);
        this.sourceCode = sourceCode;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return sourceCode;
    }
}
