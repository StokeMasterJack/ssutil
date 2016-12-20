package com.smartsoft.ssutil.servlet.http;

import com.smartsoft.ssutil.Entry;
import com.smartsoft.ssutil.EntryList;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public abstract class EntryListWriter extends EntryListMarsheller {

    protected abstract String encode(String s);

    public void serialize(EntryList entryList,Writer writer){
        PrintWriter out = new PrintWriter(writer);
        for(int i=0;i<entryList.size();i++){
            Entry entry = entryList.get(i);
            String n = entry.getName();
            String v = entry.getValue();
            v = encode(v);
            out.print(n + getNameValueSeparator() + v);
            if(i!=entryList.size()-1) out.print(getEntrySeparator());
        }
        out.flush();
    }

    public String serialize(EntryList entryList){
        StringWriter stringWriter = new StringWriter();
        serialize(entryList,stringWriter);
        return stringWriter.toString();
    }


}
