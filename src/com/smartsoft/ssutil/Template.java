package com.smartsoft.ssutil;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class Template {

    private static final String CR_LF = CrLf.CR_LF_STRING;
    private static final String LF = CrLf.LF_STRING;

    private String newLine = CrLf.LF_STRING;

    private StringBuilder template = new StringBuilder();


    public Template() {
    }

    public void setTemplateText(String templateText) {
        template = new StringBuilder(templateText);
    }

    public void print(String text) {
        template.append(text);
    }

    public void println(String line) {
        print(line);
        println();
    }

    public void println() {
        print(newLine);
    }

    public void setNewLine(String newLine) {
        this.newLine = newLine;
    }

    public void setNewLineCrLf() {
        setNewLine(CR_LF);
    }

    public void setNewLineLf() {
        setNewLine(LF);
    }

    public String format(Map<String, String> params) {
        checkNotNull(newLine);
        checkNotNull(params, "params must not be null");

        String out = template.toString().trim();
        for (Map.Entry<String, String> param : params.entrySet()) {
            checkNotNull(out);
            String paramName = param.getKey();
            checkNotNull(paramName, "null paramName is not allowed!");
            String paramValue = param.getValue();
            if (paramValue == null) {
                paramValue = "";
            }
            String placeHolder = "${" + paramName + "}";
            out = out.replace(placeHolder, paramValue);
        }

        return out;
    }

    public static Map<String, String> buildSampleParams() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("firstName", "Dave");
        map.put("lastName", "Ford");
        return map;
    }

    public static void main(String[] args) {
        executeSample();
    }


    public static void executeSample() {
        Template template = buildSampleTemplate();
        Map<String, String> map = buildSampleParams();
        String output = template.format(map);
        checkState(expectedOutput.equals(output));
        System.err.println(output);
    }

    public static Template buildSampleTemplate() {
        Template out = new Template();
        out.println("Hello my name is ${firstName} and my last name is ${lastName}.");
        out.println("And my last name is ${lastName}");
        out.println("My full name is  ${firstName} ${lastName}");
        return out;
    }

    private static Logger log = Logger.getLogger(Template.class.getName());

    static String expectedOutput = "Hello my name is Dave and my last name is Ford.\n" +
            "And my last name is Ford\n" +
            "My full name is  Dave Ford";
}
