package com.getir.readingisgood.advice.exception;

import org.springframework.http.HttpStatus;

import static com.getir.readingisgood.advice.constants.ErrorCodes.EMAIL_ALREADY_TAKEN;

public class EmailAlreadyTakenException extends BaseRuntimeException {
    public EmailAlreadyTakenException() {
        super(EMAIL_ALREADY_TAKEN, HttpStatus.BAD_REQUEST, "Email is already taken!");
    }
}
