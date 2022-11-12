package com.getir.readingisgood.converter;

import com.getir.readingisgood.model.dto.BookDto;
import com.getir.readingisgood.persistence.entity.BookEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookEntityConverter {
    public static BookDto toMapBookDto(final BookEntity bookEntity) {
        return BookDto.builder()
                .bookId(bookEntity.getId())
                .bookName(bookEntity.getName())
                .stockOfBook(bookEntity.getStock())
                .build();
    }
}
