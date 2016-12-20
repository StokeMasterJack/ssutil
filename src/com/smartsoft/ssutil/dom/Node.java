package com.smartsoft.ssutil.dom;

public abstract class Node {

    private Element parent;

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public Element getParent() {
        return parent;
    }

    public int getDepth() {
        if (parent == null) {
            return 0;
        } else {
            return parent.getDepth() + 1;
        }
    }

    abstract public void render(StringBuilder out);

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        render(out);
        return out.toString();
    }


}
