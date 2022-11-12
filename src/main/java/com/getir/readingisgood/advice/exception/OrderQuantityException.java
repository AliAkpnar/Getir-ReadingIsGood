package com.getir.readingisgood.advice.exception;

import org.springframework.http.HttpStatus;

import static com.getir.readingisgood.advice.constants.ErrorCodes.QUANTITY_MIN_ZERO;

public class OrderQuantityException extends BaseRuntimeException {
    public OrderQuantityException() {
        super(QUANTITY_MIN_ZERO, HttpStatus.BAD_REQUEST, "Quantity must not be than zero!");
    }
}
