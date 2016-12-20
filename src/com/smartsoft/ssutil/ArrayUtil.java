package com.smartsoft.ssutil;

import java.util.*;

public class ArrayUtil {

    public static String[] getIntersection(String[] a1,String[] a2) {
        if(a1 == null || a2 == null || a1.length == 0 || a2.length == 0) return new String[0];
        ArrayList intersection = new ArrayList();
        java.util.List a = new ArrayList(Arrays.asList(a2));
        for (int i = 0; i < a1.length; i++) {
            if(a.contains(a1[i])){
                intersection.add(a1[i]);
            }
        }
        return (String[]) intersection.toArray(new String[intersection.size()]);
    }

    public static String[] getUnion(String[] a1,String[] a2) {
        if(a1 == null || a1.length == 0) return (String[]) a2.clone();
        if(a2 == null || a2.length == 0) return (String[]) a1.clone();
        List newList = new ArrayList(Arrays.asList(a1));
        for (int i = 0; i < a2.length; i++) {
            if(!newList.contains(a2[i])){
                newList.add(a2[i]);
            }
        }
        return (String[]) newList.toArray(new String[newList.size()]);
    }

}
