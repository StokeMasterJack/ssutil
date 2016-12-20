package com.smartsoft.ssutil.servlet;

import com.smartsoft.ssutil.servlet.http.RequestInterrogator;
import com.smartsoft.ssutil.servlet.http.headers.CacheUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class CacheFilter implements Filter {

    private ServletContext application;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.application = config.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        RequestInterrogator r = new RequestInterrogator(request, response);

        if (request.getMethod().equalsIgnoreCase("GET")) {

            String uri = request.getRequestURI();

            if (r.isDotCacheFile()) {
                log.fine("Cache Headers added[CacheForever] for uri[" + uri + "]");
                CacheUtil.addCacheForeverResponseHeaders(response);
            } else if (r.isDotNocacheFile()) {
                log.fine("Cache Headers added[CacheNever] for uri[" + uri + "]");
                CacheUtil.addCacheNeverResponseHeaders(response);
            } else if (uri.contains("gwt/chrome")) {
                CacheUtil.addCacheForXDaysResponseHeaders(response, 30);
            } else if (uri.contains("help_16.png")) {
                CacheUtil.addCacheForeverResponseHeaders(response);
            } else {
                log.fine("Cache Headers added[None] for uri[" + uri + "]");
            }
        }

        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {

    }

    private static Logger log = Logger.getLogger(CacheFilter.class.getName());

}