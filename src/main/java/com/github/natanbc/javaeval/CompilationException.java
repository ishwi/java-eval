package com.github.natanbc.javaeval;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.util.Collections;
import java.util.List;

public class CompilationException extends RuntimeException {
    private final String compilerOutput;
    private final List<Diagnostic<? extends JavaFileObject>> diagnostics;

    CompilationException(String compilerOutput, List<Diagnostic<? extends JavaFileObject>> diagnostics) {
        this.compilerOutput = compilerOutput;
        this.diagnostics = diagnostics;
    }

    CompilationException() {
        this(null, Collections.<Diagnostic<? extends JavaFileObject>>emptyList());
    }

    /**
     * Possibly null string containing the compiler output
     *
     * @return The compiler output
     */
    public String getCompilerOutput() {
        return compilerOutput;
    }

    /**
     * List of warnings issued by the compiler
     *
     * @return The warnings issued by the compiler
     */
    public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {
        return diagnostics;
    }
}
