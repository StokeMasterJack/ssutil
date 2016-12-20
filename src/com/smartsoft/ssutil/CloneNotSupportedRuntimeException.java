package com.smartsoft.ssutil;

public class CloneNotSupportedRuntimeException extends RuntimeException {


    public CloneNotSupportedRuntimeException() {
    }

    public CloneNotSupportedRuntimeException(CloneNotSupportedException cause) {
        super(cause);
    }

    public CloneNotSupportedRuntimeException(String message) {
        super(message);
    }

    public CloneNotSupportedRuntimeException(String message, CloneNotSupportedException cause) {
        super(message, cause);
    }
}
