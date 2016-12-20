package com.smartsoft.ssutil;

import java.io.*;

public class StringPrintWriter extends PrintWriter{


    public StringPrintWriter() {
        super(new StringWriter(), true);

    }

    public String toString() {
        StringWriter stringWriter = (StringWriter) this.out;
        return stringWriter.toString();
    }


}
