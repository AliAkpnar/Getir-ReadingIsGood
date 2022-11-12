package com.getir.readingisgood.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long bookId;
    private String bookName;
    private int stockOfBook;
}
