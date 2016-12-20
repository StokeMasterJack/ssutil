package com.smartsoft.ssutil;

import java.io.IOException;

public class MyAppendable {

    private final Appendable appendable;

    public MyAppendable(Appendable appendable) {
        this.appendable = appendable;
    }

    public MyAppendable append(CharSequence csq) throws IOException {
        appendable.append(csq);
        return this;
    }

    public MyAppendable append(CharSequence csq, int start, int end) throws IOException {
        appendable.append(csq, start, end);
        return this;
    }

    public MyAppendable append(char c) throws IOException {
        appendable.append(c);
        return this;
    }

    public static Appendable append(Appendable appendable, CharSequence csq) {
        try {
            return appendable.append(csq);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Appendable append(Appendable appendable, CharSequence csq, int start, int end) {
        try {
            return appendable.append(csq, start, end);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Appendable append(Appendable appendable, char c) {
        try {
            return appendable.append(c);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
