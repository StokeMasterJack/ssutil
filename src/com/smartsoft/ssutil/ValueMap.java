package com.smartsoft.ssutil;

import java.util.*;

public class ValueMap extends HashMap{

    public <T> T get(String key){
        return (T) super.get(key);
    }

}
