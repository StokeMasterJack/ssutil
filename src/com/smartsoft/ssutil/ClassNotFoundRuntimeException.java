package com.smartsoft.ssutil;

public class ClassNotFoundRuntimeException extends RuntimeException {

    public ClassNotFoundRuntimeException() {
    }

    public ClassNotFoundRuntimeException(ClassNotFoundException cause) {
        super(cause);
    }

    public ClassNotFoundRuntimeException(String message) {
        super(message);
    }

    public ClassNotFoundRuntimeException(String message, ClassNotFoundException cause) {
        super(message, cause);
    }
}
