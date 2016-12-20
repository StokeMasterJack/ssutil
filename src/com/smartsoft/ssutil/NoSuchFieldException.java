package com.smartsoft.ssutil;

public class NoSuchFieldException extends RuntimeException {

    public NoSuchFieldException(String fieldName) {
        super(fieldName);
    }

    public String getFieldName() throws Exception {
        return getMessage();
    }

    


}
