package com.getir.readingisgood.model.request;

import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchBookRequest {
    @Min(value = 1, message = "Book must have at least one!")
    private long bookId;
    @Min(value = 0, message = "Book quantity must be positive number!")
    private int stockOfBook;
}
