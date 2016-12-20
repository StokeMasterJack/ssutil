package com.smartsoft.ssutil;

public class IllegalAccessRuntimeException extends RuntimeException {
    public IllegalAccessRuntimeException() {
    }

    public IllegalAccessRuntimeException(Throwable cause) {
        super(cause);
    }

    public IllegalAccessRuntimeException(String message) {
        super(message);
    }

    public IllegalAccessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
