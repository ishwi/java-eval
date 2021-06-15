package com.github.natanbc.javaeval;

import com.github.natanbc.javaeval.internal.ListingDiagnosticListener;
import com.github.natanbc.javaeval.internal.OutputJavaFileObject;
import com.github.natanbc.javaeval.internal.StringJavaFileObject;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JavaEvaluator {
    /**
     * Compiles java code
     *
     * @param listener a diagnostic listener; if {@code
     * null} use the compiler's default method for reporting
     * diagnostics
     * @param out a Writer for additional output from the compiler;
     * use {@code System.err} if {@code null}
     * @param options compiler options, {@code null} means no options
     * @param classes names of classes to be processed by annotation
     * processing, {@code null} means no class names
     * @param className The name of the class being compiled
     * @param code Code to evaluate
     *
     * @return The result of this compilation
     *
     * @throws CompilationException if there's an error compiling
     */
    public CompilationResult compile(DiagnosticListener<? super JavaFileObject> listener, Writer out, Iterable<String> options, Iterable<String> classes, String className, String code) {
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
        JavaFileManager jfm = jc.getStandardFileManager(listener, null, null);

        SimpleFileManager m = createManager(jfm);
        JavaCompiler.CompilationTask t = jc.getTask(
                out,
                m,
                listener,
                options,
                classes,
                Collections.singletonList(new StringJavaFileObject(className.replace('.', '/'), code))
        );
        if(t.call()) {
            Map<String, byte[]> map = new HashMap<>();
            for(OutputJavaFileObject o : m.getGeneratedOutputFiles()) {
                map.put(o.getClassName(), o.getBytes());
            }
            return new CompilationResult(Collections.unmodifiableMap(map), null, Collections.emptyList());
        } else {
            throw new CompilationException();
        }
    }

    /**
     * Compiles java code
     *
     * @param options compiler options, {@code null} means no options
     * @param classes names of classes to be processed by annotation
     * processing, {@code null} means no class names
     * @param className The name of the class being compiled
     * @param code Code to evaluate
     *
     * @return The result of this compilation
     *
     * @throws CompilationException if there's an error compiling
     *
     * @see #compile(DiagnosticListener, Writer, Iterable, Iterable, String, String)
     */
    public CompilationResult compile(Iterable<String> options, Iterable<String> classes, String className, String code) {
        StringWriter sw = new StringWriter();
        ListingDiagnosticListener listener = new ListingDiagnosticListener();
        try {
            Map<String, byte[]> map = compile(listener, sw, options, classes, className, code).classes();
            String s = sw.toString();
            return new CompilationResult(map, s.isEmpty() ? null : s, Collections.unmodifiableList(listener.getDiagnostics()));
        } catch(CompilationException e) {
            String s = sw.toString();
            throw new CompilationException(s.isEmpty() ? null : s, Collections.unmodifiableList(listener.getDiagnostics()));
        }
    }

    /**
     * Compiles java code
     *
     * @param className The name of the class being compiled
     * @param code Code to evaluate
     *
     * @return The result of this compilation
     *
     * @throws CompilationException if there's an error compiling
     *
     * @see #compile(Iterable, Iterable, String, String)
     */
    public CompilationResult compile(String className, String code) {
        return compile(null, null, className, code);
    }

    /**
     * Creates a new {@link CompilationRequest}, useful for chaining calls and only setting wanted options
     *
     * @return a new {@link com.github.natanbc.javaeval.CompilationRequest}
     */
    public CompilationRequest compile() {
        return new CompilationRequest(this);
    }

    /**
     * Creates a new {@link SimpleFileManager}, with a fallback to the {@link JavaFileManager} the JVM provides.
     *
     * @param manager The {@link JavaFileManager} provided by the JVM
     *
     * @return an instance of {@link SimpleFileManager}
     */
    protected SimpleFileManager createManager(JavaFileManager manager) {
        return new SimpleFileManager(manager);
    }
}
