package com.example.BlogApplication.Exception;

public class CommonViewAddException extends RuntimeException{
    public CommonViewAddException() {
        super("Common View Exception in Adding");
    }

    public CommonViewAddException(Throwable cause) {
        super("Common View Exception in Adding", cause);
    }
}
