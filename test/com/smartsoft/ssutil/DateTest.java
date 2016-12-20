package com.smartsoft.ssutil;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class DateTest {

    @Test
    public void test1() throws Exception {
        int year = 2016;
        int week = 17;
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.setFirstDayOfWeek(Calendar.MONDAY);
        startDate.set(Calendar.YEAR, year);
        startDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        startDate.set(Calendar.WEEK_OF_YEAR, week);

        java.util.Date d1 = startDate.getTime();
        System.err.println("d1[" + d1 + "]");

        com.smartsoft.ssutil.Date d2 = new com.smartsoft.ssutil.Date(d1);
        System.err.println("d2[" + d2 + "]");

        java.util.Date d3 = d2.toUtilDate();
        System.err.println("d3[" + d3 + "]");
    }
}
