package com.example.BlogApplication.Exception;


public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String s) {
        super(s);
    }

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
