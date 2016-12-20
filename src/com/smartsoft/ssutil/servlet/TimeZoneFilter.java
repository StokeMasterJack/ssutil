package com.smartsoft.ssutil.servlet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneFilter implements Filter {

    private String  sTimeZone;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        sTimeZone = filterConfig.getInitParameter("timeZone");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(sTimeZone != null){
            TimeZone.setDefault(TimeZone.getTimeZone(sTimeZone));
        }


        Locale.setDefault(Locale.US);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
