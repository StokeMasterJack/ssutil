package com.smartsoft.ssutil.servlet.http;



/**
 * This class builds an EntryList from url-encoded name/value pairs (i.e. a query string).
 *      URL Encoded Params look like this:
 *      name=urlEncodedValue&name=urlEncodedValue&name=urlEncodedValue
 */
public class QueryStringWriter extends EntryListWriter{

    protected String encode(String s) {
        return HttpUtil.urlEncode(s);
    }

    protected String getEntrySeparator() {
        return "&";
    }

    protected String getNameValueSeparator() {
        return "=";
    }


}
