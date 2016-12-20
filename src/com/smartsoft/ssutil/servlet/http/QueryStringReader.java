package com.smartsoft.ssutil.servlet.http;

import com.smartsoft.ssutil.*;

/**
 * This class builds an EntryList from url-encoded name/value pairs (i.e. a query string).
 *      URL Encoded Params look like this:
 *      name=urlEncodedValue&name=urlEncodedValue&name=urlEncodedValue
 */
public class QueryStringReader extends EntryListReader{

    protected String getEndFlag(){
        return CrLf.asString + CrLf.asString;
    }

    protected String getEntrySeparator(){
        return "&";
    }

    protected String getNameValueSeparator(){
        return "=";
    }

    protected String decode(String s){
        return HttpUtil.urlDecode(s);
    }


}
