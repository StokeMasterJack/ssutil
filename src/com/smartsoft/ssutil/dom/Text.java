package com.smartsoft.ssutil.dom;

public class Text extends Node {

    private final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void render(StringBuilder out) {
        //todo xml escape this
        out.append(out.toString());
    }
}
