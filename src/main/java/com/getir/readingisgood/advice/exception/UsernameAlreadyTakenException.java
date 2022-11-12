package com.getir.readingisgood.advice.exception;

import org.springframework.http.HttpStatus;

import static com.getir.readingisgood.advice.constants.ErrorCodes.USERNAME_ALREADY_TAKEN;

public class UsernameAlreadyTakenException extends BaseRuntimeException {
    public UsernameAlreadyTakenException() {
        super(USERNAME_ALREADY_TAKEN, HttpStatus.BAD_REQUEST, "Username is already taken!");
    }
}
