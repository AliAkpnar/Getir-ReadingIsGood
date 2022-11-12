package com.getir.readingisgood.advice.exception;

import org.springframework.http.HttpStatus;

import static com.getir.readingisgood.advice.constants.ErrorCodes.CUSTOMER_ALREADY_EXIST;

public class CustomerAlreadyExistsException extends BaseRuntimeException {
    public CustomerAlreadyExistsException() {
        super(CUSTOMER_ALREADY_EXIST, HttpStatus.CONFLICT, "Customer already exists!");
    }
}
