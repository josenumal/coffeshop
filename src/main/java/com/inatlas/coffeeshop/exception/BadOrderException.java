package com.inatlas.coffeeshop.exception;

public class BadOrderException extends RuntimeException {

    public BadOrderException(final String message) {
        super(message);
    }
}
