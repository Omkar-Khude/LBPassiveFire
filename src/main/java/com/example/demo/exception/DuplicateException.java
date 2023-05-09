package com.example.demo.exception;

@SuppressWarnings("serial")
public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }

}
