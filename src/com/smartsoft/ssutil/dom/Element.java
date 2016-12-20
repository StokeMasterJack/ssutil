package com.smartsoft.ssutil.dom;

import com.smartsoft.ssutil.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class Element extends Node {

    private final String name;
    protected final ArrayList<Node> content = new ArrayList<Node>();
    private final HashMap<String, String> atts = new HashMap<String, String>();

    public Element(String name) {
        this(name, null);
    }

    public Element(String name, String cssClass) {
        this.name = name;
        if (cssClass != null && !cssClass.trim().equals("")) {
            atts.put("class", cssClass);
        }
    }

    public void add(String text) {
        Text textNode = new Text(text);
        textNode.setParent(this);
        content.add(textNode);
    }

    public void add(Element child) {
        content.add(child);
        child.setParent(this);
    }

    public Span addSpan(String cssClass, String content) {
        Span span = new Span(cssClass, content);
        add(span);
        return span;
    }

    public void attr(String name, String value) {
        atts.put(name, value);
    }


    public boolean isDiv() {
        return name.equals("div");
    }

    public Div addDiv(String cssClass) {
        return new Div(cssClass);
    }

    @Override
    public void render(StringBuilder out) {
        String indent = StringUtil.tab(getDepth());

//        if (isDiv()) {
//            out.append("\n").append(indent);
//        }

        out.append("<").append(name);

        if (!atts.isEmpty()) {

            for (String attrName : atts.keySet()) {
                out.append(" ");
                out.append(attrName);
                out.append("=\"");
                out.append(atts.get(attrName));
                out.append("\"");
            }
        }

        out.append(">");

        for (Node child : content) {
            child.render(out);
        }

//        if (isDiv()) {
//            out.append("\n").append(indent);
//        }
        out.append("</").append(name).append(">");

    }


}
