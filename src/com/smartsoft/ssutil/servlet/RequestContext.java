package com.smartsoft.ssutil.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class RequestContext {

    public final HttpServletRequest request;
    public final HttpServletResponse response;
    public final Throwable exception;

    public RequestContext(HttpServletRequest request, HttpServletResponse response, Throwable exception) {
        checkNotNull(request);
        checkNotNull(response);
        checkNotNull(exception);

        this.request = request;
        this.response = response;
        this.exception = exception;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return getResponse().getOutputStream();
    }

    public Throwable getException() {
        return exception;
    }

    public String getRequestUri() {
        return request.getRequestURI();
    }

    public String getQueryString() {
        return request.getQueryString();
    }


}
