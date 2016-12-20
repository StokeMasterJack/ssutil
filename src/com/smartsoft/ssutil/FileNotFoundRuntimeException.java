package com.smartsoft.ssutil;

public class FileNotFoundRuntimeException extends RuntimeException{

    public FileNotFoundRuntimeException() {
    }

    public FileNotFoundRuntimeException(Throwable cause) {
        super(cause);
    }

    public FileNotFoundRuntimeException(String message) {
        super(message);
    }

    public FileNotFoundRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
