package com.smartsoft.ssutil.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class RequestHandler {

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected void init(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    protected abstract void handle() throws Exception;

    public void handle(HttpServletRequest request, HttpServletResponse response) {
        init(request, response);
        try {

            handle();
            request.setAttribute("rh", this);
            request.setAttribute("contextPath", getContextPath());
        } catch (Exception e) {
            send500Error(e);
        }
    }

    public void send500Error(Exception e) {
        response.setStatus(500);
        response.setContentType("text/plain");
        try {
            PrintWriter out = response.getWriter();
            e.printStackTrace(out);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }
    }

    public void send400Error(String msg) {
        response.setStatus(400);
        response.setContentType("text/plain");
        try {
            PrintWriter out = response.getWriter();
            out.println(msg);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public String getContextPath() {
        return getRequest().getContextPath();
    }
}
