package com.smartsoft.ssutil;

/**
 * Used to for html select tag
 */
public class Option{

    private String submitValue;
    private String displayText;

    public Option(String submitValue, String  displayText) {
        this.submitValue = submitValue;
        this.displayText = displayText;
    }

    public Option(String submitValue) {
        this(submitValue,submitValue);
    }

    public String getSubmitValue() {
        return submitValue;
    }

    public void setSubmitValue(String submitValue) {
        this.submitValue = submitValue;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String  displayText) {
        this.displayText = displayText;
    }

}
