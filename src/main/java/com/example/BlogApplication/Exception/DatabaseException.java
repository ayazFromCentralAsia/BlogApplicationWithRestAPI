package com.example.BlogApplication.Exception;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
