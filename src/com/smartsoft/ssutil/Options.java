package com.smartsoft.ssutil;

import java.util.*;

public class Options {

    private List list = new ArrayList();
    private Option defaultOption;

    public Options() {
    }


    public Options(String[] values) {
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            add(value);
        }
    }

    public void prepend(Option option){
        list.add(0,option);
    }

    public void add(Option option){
        list.add(option);
    }

    public void add(String option){
        add(option,option);
    }

    public void add(String submitValue,String displayText){
        add(new Option(submitValue,displayText));
    }

    public List<Option> toOptionList(){
        return list;
    }

    public Option[] toArray(){
        Option[] a = new Option[list.size()];
        list.toArray(a);
        return a;
    }

    public void add(List list) {
        for (int i = 0; i < list.size(); i++) {
            Object o = (Object) list.get(i);
            if(o == null) add("");
            else add(o.toString());
        }
    }

    public Option getDefaultOption() {
        return defaultOption;
    }

    public void setDefaultOption(Option defaultOption) {
        this.defaultOption = defaultOption;
    }


}
