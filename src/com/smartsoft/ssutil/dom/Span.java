package com.smartsoft.ssutil.dom;

public class Span extends Element {
    public Span(String cssClass) {
        super("span", cssClass);
    }

    public Span(String cssClass,String content) {
        super("span", cssClass);
        add(content);
    }
}
