package com.github.natanbc.javaeval;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CompilationRequest {
    private final JavaEvaluator evaluator;
    private Set<String> compilerOptions;
    private Set<String> annotationProcessing;
    private String className;
    private String sourceCode;

    public CompilationRequest(JavaEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public CompilationRequest source(String className, String sourceCode) {
        Objects.requireNonNull(className, "className cannot be null");
        Objects.requireNonNull(sourceCode, "sourceCode cannot be null");
        this.className = className;
        this.sourceCode = sourceCode;
        return this;
    }

    public CompilationRequest addCompilerOptions(String... options) {
        if(compilerOptions == null) compilerOptions = new HashSet<>();
        compilerOptions.addAll(Arrays.asList(options));
        return this;
    }

    public CompilationRequest addClassesForAnnotationProcessing(String... classesToBeProcessed) {
        if(annotationProcessing == null) annotationProcessing = new HashSet<>();
        annotationProcessing.addAll(Arrays.asList(classesToBeProcessed));
        return this;
    }

    public CompilationResult execute() {
        if(sourceCode == null) throw new IllegalStateException("Source code not set");
        return evaluator.compile(compilerOptions, annotationProcessing, className, sourceCode);
    }
}
