package com.example.BlogApplication.Exception;

public class CommonViewSavingException extends RuntimeException{
    public CommonViewSavingException() {
        super("Common View Saving Exception");
    }

    public CommonViewSavingException(Throwable cause) {
        super("Common View Saving Exception", cause);
    }
}
