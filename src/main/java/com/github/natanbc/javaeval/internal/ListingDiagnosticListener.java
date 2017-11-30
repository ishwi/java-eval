package com.github.natanbc.javaeval.internal;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import java.util.LinkedList;
import java.util.List;

public class ListingDiagnosticListener implements DiagnosticListener<JavaFileObject> {
    private final List<Diagnostic<? extends JavaFileObject>> diagnostics = new LinkedList<>();

    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
        diagnostics.add(diagnostic);
    }

    public List<Diagnostic<? extends JavaFileObject>> getDiagnostics() {
        return diagnostics;
    }
}
