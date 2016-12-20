package com.smartsoft.ssutil.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ExceptionRenderer {

    public static String render(Throwable e) {
        return com.smartsoft.ssutil.ExceptionRenderer.render(e);
    }

    /**
     * Automatically escapes html special characters
     */
    public static String renderAsHtmlComment(Throwable exceptionObject) {
        return com.smartsoft.ssutil.ExceptionRenderer.renderAsHtmlComment(exceptionObject);
    }

    public static void renderException(HttpServletResponse response, Throwable e) {
        renderException(response, e, "No title");
    }

    public static void renderException(HttpServletResponse response, Throwable e, String title) {
        response.reset();
        response.setStatus(500);
        response.setContentType("text/plain");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }
        out.println("ERROR PROCESSING REQUEST");
        out.println("=============================");
        out.println("***  " + title + "  ***");
        out.println("=============================");
        if (e != null)
            e.printStackTrace(out);
    }

    public static void renderException(PrintWriter out, Throwable e, String title) {
        com.smartsoft.ssutil.ExceptionRenderer.renderException(out, e, title);
    }

    public static void renderException(PrintWriter out, Throwable e) {
        com.smartsoft.ssutil.ExceptionRenderer.renderException(out, e);
    }

    public static void renderStackTraceAsPlainText(PrintWriter a, Throwable e) {
        e.printStackTrace();
    }


}

