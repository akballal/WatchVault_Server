package com.movierepo.exception;

public class IncorrectUsernameOrPasswordException extends RuntimeException {

    private String message;

    public IncorrectUsernameOrPasswordException() {
    }

    public IncorrectUsernameOrPasswordException(String msg) {
        super(msg);
        this.message = msg;
    }
}
