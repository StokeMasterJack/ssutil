package com.smartsoft.ssutil;

public class InstantiationRuntimeException extends RuntimeException {

    public InstantiationRuntimeException() {
    }

    public InstantiationRuntimeException(Throwable cause) {
        super(cause);
    }

    public InstantiationRuntimeException(String message) {
        super(message);
    }

    public InstantiationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
