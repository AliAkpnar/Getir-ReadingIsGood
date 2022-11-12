package com.getir.readingisgood.advice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public class BaseRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int code;
    private final HttpStatus status;

    public BaseRuntimeException(int code, String message) {
        this(code,NOT_FOUND,message);
    }

    public BaseRuntimeException(int code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
