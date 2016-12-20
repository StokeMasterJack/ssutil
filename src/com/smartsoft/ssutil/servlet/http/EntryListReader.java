package com.smartsoft.ssutil.servlet.http;

import com.smartsoft.ssutil.EntryList;
import com.smartsoft.ssutil.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public abstract class EntryListReader extends EntryListMarsheller {

    public EntryList parse(InputStream is) throws IOException{
        String endFlag = getEndFlag();
        String parameters = StringUtil.readUntil(is,endFlag,2000);
        return parse(parameters);
    }

    public EntryList parse(Reader reader) throws IOException{
        String endFlag = getEndFlag();
        String parameters = StringUtil.readUntil(reader,endFlag,2000);
        return parse(parameters);
    }

    public EntryList parse(String parameters) {
        EntryList entryList = new EntryList();
        if(StringUtil.isBlank(parameters)) return entryList;
        parameters = parameters.trim();
        String[] parameterArray = parameters.split(getEntrySeparator());
        for(int i=0;i<parameterArray.length;i++){
            String nv = parameterArray[i];
            String[] nvArray = nv.split(getNameValueSeparator());
            String n = nvArray[0].trim();
            String v;
            if(nvArray.length == 2){
                v = nvArray[1];
                if(v!=null) {
                    v = v.trim();
                }
                else{
                    v = "";
                }
            }
            else{
                v = "";
            }
            n = decode(n);
            v = decode(v);
            entryList.add(n,v);
        }
        return entryList;
    }

    protected abstract String getEndFlag();

    protected abstract String decode(String s);


}
