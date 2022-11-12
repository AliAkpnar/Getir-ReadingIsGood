package com.getir.readingisgood.service;

import com.getir.readingisgood.model.dto.BookDto;
import com.getir.readingisgood.model.request.PatchBookRequest;
import com.getir.readingisgood.model.request.BookRequest;

public interface BookService {
    BookDto saveBook(BookRequest bookRequest);

    BookDto updateBook(PatchBookRequest patchBookRequest);
}
