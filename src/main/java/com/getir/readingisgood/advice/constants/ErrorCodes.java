package com.getir.readingisgood.advice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCodes {
    public static final int CUSTOMER_ALREADY_EXIST = 101;
    public static final int QUANTITY_MIN_ZERO = 102;
    public static final int CUSTOMER_NOT_FOUND = 103;
    public static final int ORDER_NOT_FOUND = 104;
    public static final int BOOK_NOT_FOUND = 105;
    public static final int VALIDATION_ERROR = 106;
    public static final int MANAGEMENT = 107;
    public static final int EMAIL_ALREADY_TAKEN = 108;
    public static final int USERNAME_ALREADY_TAKEN = 109;
}
