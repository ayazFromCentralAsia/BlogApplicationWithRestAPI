package com.example.BlogApplication.Exception;

public class CommonViewUpdateException extends RuntimeException{
    public CommonViewUpdateException() {
        super("View Update Exception");
    }

    public CommonViewUpdateException(Throwable cause) {
        super("View Update Exception ",cause);
    }
}
