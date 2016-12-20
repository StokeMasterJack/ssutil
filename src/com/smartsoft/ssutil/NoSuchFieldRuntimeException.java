package com.smartsoft.ssutil;

public class NoSuchFieldRuntimeException extends RuntimeException{

    public NoSuchFieldRuntimeException() {
    }

    public NoSuchFieldRuntimeException(Throwable cause) {
        super(cause);
    }

    public NoSuchFieldRuntimeException(String message) {
        super(message);
    }

    public NoSuchFieldRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
