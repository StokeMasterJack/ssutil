package com.smartsoft.ssutil;

public class RemoteHostRuntimeException extends RuntimeException {

    public RemoteHostRuntimeException() {
    }

    public RemoteHostRuntimeException(Throwable cause) {
        super(cause);
    }

    public RemoteHostRuntimeException(String message) {
        super(message);
    }

    public RemoteHostRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
