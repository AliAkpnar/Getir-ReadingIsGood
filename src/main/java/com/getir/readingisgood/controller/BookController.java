package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.dto.BookDto;
import com.getir.readingisgood.model.request.PatchBookRequest;
import com.getir.readingisgood.model.request.BookRequest;
import com.getir.readingisgood.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookController extends BaseController {

    private final BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<BookDto> saveBook(@Valid @RequestBody BookRequest bookRequest) {
        log.info("Save Book is started : {}", bookRequest);
        return ResponseEntity.ok(bookService.saveBook(bookRequest));
    }

    @PatchMapping("/book")
    public ResponseEntity<BookDto> updateBook(@Valid @RequestBody PatchBookRequest patchBookRequest) {
        log.info("Update Book is started : {}", patchBookRequest);
        return ResponseEntity.ok(bookService.updateBook(patchBookRequest));
    }

}
