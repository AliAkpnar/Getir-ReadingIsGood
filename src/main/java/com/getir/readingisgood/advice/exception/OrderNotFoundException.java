package com.getir.readingisgood.advice.exception;

import org.springframework.http.HttpStatus;

import static com.getir.readingisgood.advice.constants.ErrorCodes.ORDER_NOT_FOUND;

public class OrderNotFoundException extends BaseRuntimeException {
    public OrderNotFoundException() {
        super(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND, "Order Not Found!");
    }
}
