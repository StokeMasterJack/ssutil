package com.smartsoft.ssutil.servlet.http.headers;

import com.smartsoft.ssutil.Date;
import com.smartsoft.ssutil.servlet.http.HttpUtil;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

public class Expires {


    private String headerValue;

    public Expires() {
        this(new Date().addMonths(1));
    }

    public Expires(int year, int month, int dayOfMonth) {
        this(new Date(year, month, dayOfMonth));
    }

    public Expires(com.smartsoft.ssutil.Date expiresDate) {
        java.util.Date d1 = new java.util.Date();
        java.util.Date d2 = expiresDate.toUtilDate();

        String d1Gmt = HttpUtil.getHttpGmtString(d1);
        String d2Gmt = HttpUtil.getHttpGmtString(d2);

//        System.err.println("d1Gmt[" + d1Gmt + "]");
//        System.err.println("d2Gmt[" + d2Gmt + "]");

        headerValue = d2Gmt;
    }

    public Expires(java.util.Date expiresDate) {
        headerValue = HttpUtil.getHttpGmtString(expiresDate);
    }


    public static Expires expiresXHoursFromNow(int hoursFromNow) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, hoursFromNow);
        java.util.Date d2 = c.getTime();

//            String d1Gmt = HttpUtil.getHttpGmtString(d1);
//             String d2Gmt = HttpUtil.getHttpGmtString(d2);

        return new Expires(d2);
    }

    public String getHeaderName() {
        return "Expires";
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public String getHeader() {
        return getHeaderName() + ": " + getHeaderValue();
    }


    public static Expires expiresOneMonthsFromNow() {
        return expiresXMonthsFromNow(1);
    }

    public static Expires expiresOneYearFromNow() {
        return expiresXMonthsFromNow(12);
    }

    public static Expires expiresXMonthsFromNow(int monthFromNow) {
        Date today = new Date();
        return new Expires(today.addMonths(monthFromNow));
    }

    public static Expires expiresXDaysFromNow(int daysFromNow) {
        Date today = new Date();
        return new Expires(today.addDays(daysFromNow));
    }

    public static Expires expiresLastWeek() {
        Date today = new Date();
        return new Expires(today.addDays(-7));
    }

    public static Expires expiresNow() {
        Date today = new Date();
        return new Expires(today);
    }

    @Override
    public String toString() {
        return getHeader();
    }

    public void addToResponse(HttpServletResponse response) {
        response.setHeader(getHeaderName(), getHeaderValue());
    }
}
