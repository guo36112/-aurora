package com.example.productserver.common;



@SuppressWarnings("serial")
public class JException extends RuntimeException {

    protected String code;

    protected String message;

    protected Throwable nestedException = null;


    public JException(String message) {
        super(message);
    }

    public JException(String code, String message) {
        this(message);
        this.code = code;
    }

}
