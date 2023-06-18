package com.inatlas.coffeeshop.exceptions;

public class BadOrderException extends RuntimeException {

    public BadOrderException(final String message) {
        super(message);
    }
}
