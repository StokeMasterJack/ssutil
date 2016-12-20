package com.smartsoft.ssutil;


import java.io.IOException;

public class ExceptionRenderer {

    public static final String NL = "\n";

    public static String render(Throwable e) {
        if (e == null) return "";
        DaveStringBuilder pw = new DaveStringBuilder();
        printStackTrace(e, pw);
        return pw.toString();
    }

    static class DaveStringBuilder implements Appendable {

        Appendable delegate;
        private final String newLine;

        DaveStringBuilder(Appendable sb, String newLine) {
            this.delegate = sb;
            this.newLine = newLine;
        }

        DaveStringBuilder(Appendable sb) {
            this(sb, NL);
        }


        DaveStringBuilder() {
            this(new StringBuffer(), NL);
        }


        @Override
        public Appendable append(CharSequence cs) throws IOException {
            return delegate.append(cs);
        }

        @Override
        public Appendable append(CharSequence cs, int start, int end) throws IOException {
            return delegate.append(cs, start, end);
        }

        @Override
        public Appendable append(char c) throws IOException {
            return delegate.append(c);
        }

        @Override
        public String toString() {
            return delegate.toString();
        }

        public void println(String s) {
            try {
                delegate.append(s);
                println();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void println() {
            try {
                delegate.append(newLine);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void println(Throwable x) {
            String s = String.valueOf(x);
            println(s);
        }


    }

    private static void printStackTrace(Throwable e, DaveStringBuilder s) {
        s.println(e);
        StackTraceElement[] trace = e.getStackTrace();
        for (int i = 0; i < trace.length; i++) {
            s.println("\tat " + trace[i]);
        }

        Throwable ourCause = e.getCause();
        if (ourCause != null) {
            printStackTraceAsCause(ourCause, s, trace);
        }
    }


    private static void printStackTraceAsCause(Throwable thi, DaveStringBuilder s, StackTraceElement[] causedTrace) {
        // assert Thread.holdsLock(s);

        // Compute number of frames in common between this and caused
        StackTraceElement[] trace = thi.getStackTrace();
        int m = trace.length - 1, n = causedTrace.length - 1;
        while (m >= 0 && n >= 0 && trace[m].equals(causedTrace[n])) {
            m--;
            n--;
        }
        int framesInCommon = trace.length - 1 - m;

        s.println("Caused by: " + thi);
        for (int i = 0; i <= m; i++)
            s.println("\tat " + trace[i]);
        if (framesInCommon != 0)
            s.println("\t... " + framesInCommon + " more");

        // Recurse if we have a cause
        Throwable ourCause = thi.getCause();
        if (ourCause != null) {
            printStackTraceAsCause(ourCause, s, trace);
        }
    }


    /**
     * Automatically escapes html special characters
     */
    public static String renderAsHtmlComment(Throwable exceptionObject) {
        try {

            DaveStringBuilder out = new DaveStringBuilder();

            out.println();
            out.println("<!--");
            out.println("********************************************");
            out.println("*****        Exception Report        ****** ");
            out.println("********************************************");
            if (exceptionObject == null) return "Nothing to render. Exception object is null.";
            out.println(Strings.escapeXml(render(exceptionObject)));
            out.println("********************************************");
            out.println("-->");


            return out.toString();
        } catch (Throwable ee) {
            return "<!-- error in the error handler[" + ee + "]";
        }
    }

    public static void renderException(Appendable a, Throwable e, String title) {

        DaveStringBuilder out = new DaveStringBuilder(a);
        out.println("ERROR PROCESSING REQUEST");
        out.println("=============================");
        out.println("***  " + title + "  ***");
        out.println(NL);


        if (e != null) {
            printStackTrace(e, out);
        }
    }


    public static void renderException(Appendable out, Throwable e) {
        renderException(out, e, "Smart Soft");
    }


    public static String renderExceptionToString(Throwable e, String msg) {
        StringBuilder sb = new StringBuilder();
        renderException(sb, e, msg);
        return sb.toString();
    }

    public static String renderExceptionToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        renderException(sb, e);
        return sb.toString();
    }


}

