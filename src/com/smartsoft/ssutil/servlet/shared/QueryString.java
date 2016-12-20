package com.smartsoft.ssutil.servlet.shared;

import com.google.common.collect.ImmutableMap;

public class QueryString {

    public static ImmutableMap<String, String> parse(String qs) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        String[] nvPairs = qs.split("&");
        for (String nvPair : nvPairs) {
            String[] nv = nvPair.split("=");
            builder.put(nv[0], nv[1]);
        }
        return builder.build();
    }

}
