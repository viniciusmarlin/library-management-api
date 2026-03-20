package br.com.viniciusmarlin.library.exception;

public class UserAlreadyHasActiveLoanException extends RuntimeException {

    public UserAlreadyHasActiveLoanException(String message) {
        super(message);
    }
}