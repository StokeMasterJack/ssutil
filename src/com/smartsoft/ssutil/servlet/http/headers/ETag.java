package com.smartsoft.ssutil.servlet.http.headers;

import javax.servlet.http.HttpServletResponse;

import static com.google.common.base.Preconditions.checkNotNull;


public class ETag {

    public static final String HEADER_NAME = "ETag";
    public static final String STRONG_PREFIX = "W/";

    private final boolean strong;
    private final String tag;

    public ETag(boolean strong, String tag) {
        checkNotNull(tag);
        this.strong = strong;
        this.tag = tag;
    }

    public String getHeaderValue() {
        if (strong) {
            return STRONG_PREFIX + tag;
        } else {
            return STRONG_PREFIX + tag;
        }
    }

    public String getHeaderName() {
        return HEADER_NAME;
    }

    public String getHeader() {
        return getHeaderName() + ": " + getHeaderValue();
    }

    public String toString() {
        return getHeader();
    }

    public void addToResponse(HttpServletResponse response) {
        response.setHeader(getHeaderName(), getHeaderValue());
    }
}
