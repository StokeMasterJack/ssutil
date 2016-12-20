package com.smartsoft.ssutil;



/** Represents a name-value pair
 *      Can be used for Http header fields or
 *      SMTP header fields or HTTP request parameters
 *  Ex: Content-type: text/html
 *  Ex: From: Dave Ford <dford@smart-soft.com>
 */
public class Entry implements Comparable{

    private String name;
    private String value;

    public Entry(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        if(name == null) throw new IllegalArgumentException("name cannot be null");
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString(){
        return this.name + ": " + this.value;
    }

    public int compareTo(Object o) {
        Entry e = (Entry) o;
        return this.name.compareTo(e.name);
    }

}