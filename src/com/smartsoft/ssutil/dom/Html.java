package com.smartsoft.ssutil.dom;

public class Html extends Element {

    private final String docType = "<!DOCTYPE html>";

    private Element head;
    private Element body;

    public Html() {
        super("html");
        head = createHead();
        body = createBody();
        add(head);
        add(body);
    }

    private Element createHead() {
        Element head = new Element("head");

        Element link = new Element("link");
        link.attr("rel", "stylesheet");
        link.attr("href", "csp.css");
        head.add(link);

        return head;
    }

    public Element getBody() {
        return body;
    }

    private Element createBody() {
        return new Element("body");
    }

    @Override
    public void render(StringBuilder out) {
        out.append(docType);
        super.render(out);
    }
}
