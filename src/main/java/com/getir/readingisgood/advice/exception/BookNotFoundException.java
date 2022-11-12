package com.getir.readingisgood.advice.exception;

import org.springframework.http.HttpStatus;

import static com.getir.readingisgood.advice.constants.ErrorCodes.BOOK_NOT_FOUND;

public class BookNotFoundException extends BaseRuntimeException {
    public BookNotFoundException() {
        super(BOOK_NOT_FOUND, HttpStatus.NOT_FOUND, "Book Not Found!");
    }
}
