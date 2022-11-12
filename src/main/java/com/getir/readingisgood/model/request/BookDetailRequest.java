package com.getir.readingisgood.model.request;

import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailRequest {

    private Long bookId;

    @Min(value = 1, message = "Order quantity must have at least one!")
    private int quantity;
}
