package com.demo.tree.exceptions;

public class LoginTwiceException extends RuntimeException {

    public LoginTwiceException(String message) {
        super(message);
    }
}
