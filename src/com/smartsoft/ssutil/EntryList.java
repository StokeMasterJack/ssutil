package com.smartsoft.ssutil;

import java.io.*;
import java.util.*;

/**
 * Represents a sequence of name-value (string/object) pairs
 * Used to store, for example, http header fields or http request parameters:
 * name: value CrLf name: value CrLf name: value (http header field)
 * name=urlEncodedValue&name=urlEncodedValue&name=urlEncodedValue (url encoded query string)
 * Or any other data of similar structure:
 * - name/value
 * - string/object
 * - duplicates allowed
 *
 * Basically, an ordered map that allows dups
 */
public class EntryList {

    private List<Entry> entries = new ArrayList<Entry>();    //Entry

    public static final String ANONYMOUS_FIELD_NAME = "ANONYMOUS_FIELD_NAME";

    public EntryList() {
    }

    public void add(Entry entry) {
        entries.add(entry);
    }

    public void set(Entry entry) {
        remove(entry.getName());
        add(entry);
    }

    public void add(String name, String value) {
        Entry entry = new Entry(name, value);
        this.add(entry);
    }

    public void set(String name, String  value) {
        remove(name);
        add(name, value);
    }

    public void set(int index, String  value) {
        Entry entry = entries.get(index);
        entry.setValue(value);
    }

    public void addAnonymousValue(String  value){
        add(ANONYMOUS_FIELD_NAME,value);
    }

    public void remove(String name) {
        for (int i = entries.size() - 1; i >= 0; i--) {
            Entry e = (Entry) this.get(i);
            if (e.getName().equalsIgnoreCase(name)) {
                entries.remove(i);
            }
        }
    }

    public Entry get(int index) throws NoSuchFieldException {
        if(entries.get(index) == null) throw new NoSuchFieldException(index+"");
        return entries.get(index);
    }

    public String getValue(int index) throws NoSuchFieldException{
        Entry entry = get(index);
        return entry.getValue();
    }

    public int size() {
        return entries.size();
    }

    public Entry get(String name) throws NoSuchFieldException {
        for (int i = 0; i < this.size(); i++) {
            Entry e = get(i);
            if (e.getName().equalsIgnoreCase(name)) return e;
        }
        throw new com.smartsoft.ssutil.NoSuchFieldException(name);
    }

    public String getValue(String name) throws NoSuchFieldException{
        return get(name).getValue();
    }

    public String[] getValues(String name) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < this.size(); i++) {
            Entry e = get(i);
            if (e.getName().equalsIgnoreCase(name)) {
                list.add(e.getValue());
            }
        }
        String[] s = new String[0];
        return (String[]) list.toArray(s);
    }

    public void render(PrintWriter out) {
        for (int i = 0; i < this.size(); i++) {
            out.println(this.get(i));
        }
    }

    public Entry[] toSortedArray() {
        Entry[] a = new Entry[entries.size()];
        a = (Entry[]) this.entries.toArray(a);
        Arrays.sort(a);
        return a;        
    }

    public List asValueList(){
        List<String> list = new ArrayList();
        for (int i = 0; i < entries.size(); i++) {
            Entry entry = (Entry) entries.get(i);
            list.add(entry.getValue());
        }
        return Collections.unmodifiableList(list);
    }

    public void setValues(String name, String[] values) {
        remove(name);
        if (values == null) {
            add(name, null);
        } else {
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                add(name, value);
            }
        }
    }

    public void setValues(String name,Enumeration<String> values){
        remove(name);
        while (values.hasMoreElements()) {
            String value =  values.nextElement();
            add(name, value);
        }                    
    }

    public void setEntries(Map map) {
        entries.clear();
        Set keys = map.keySet();
        for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
            String name = (String) iterator.next();
            String[] values = (String[]) map.get(name);
            setValues(name, values);
        }
    }

    public String[] getEntryNames(){
        String[] names = new String[entries.size()];
        for (int i = 0; i < entries.size(); i++) {
            Entry entry = (Entry) entries.get(i);
            names[i] = entry.getName();
        }
        return names;
    }

    public Entry[] getEntries(){
        Entry[] a = new Entry[entries.size()];
        return (Entry[])entries.toArray(a);
    }

    public String toString() {
        StringPrintWriter out = new StringPrintWriter();
        render(out);
        return out.toString();
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public void clear() {
        entries.clear();
    }

    public enum DupStatus {
        AllDups,NoDups,SomeDups
    };

    public DupStatus getDupStatus() {
        if (entries.isEmpty()) return DupStatus.NoDups;
        Set<String> set = new HashSet<String>();
        for (String key : getEntryNames()) {
            set.add(key);
        }
        if (set.size() == entries.size()) return DupStatus.NoDups;
        if (set.size() == 1) return DupStatus.AllDups;
        if (set.size() > 1 && set.size() < entries.size()) return DupStatus.SomeDups;
        throw new IllegalStateException("Huh?");
    }

}

