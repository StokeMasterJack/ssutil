package com.smartsoft.ssutil.servlet.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestInterrogator {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
//    private final File repoBaseDir;



    private final String requestUri;
//    private final String contextPath;
//
//    private final String waRelativeRequestUri;
//    public static final Map<String, String> mimeMap = new HashMap<String, String>();
//
//    private boolean gc;
//    private boolean gn;
//    private boolean vb;
//
//    private boolean cacheForever;
//    private boolean cacheNever;

    public RequestInterrogator(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.requestUri = request.getRequestURI();


    }




    public boolean isDotCacheFile() {
        return requestUri != null && requestUri.contains(".cache.");
    }

    public boolean isDotNocacheFile() {
        return requestUri != null && requestUri.contains(".nocache.");
    }


}
