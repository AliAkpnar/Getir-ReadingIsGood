package com.getir.readingisgood.service;

import com.getir.readingisgood.advice.exception.BookNotFoundException;
import com.getir.readingisgood.model.dto.BookDto;
import com.getir.readingisgood.model.request.BookRequest;
import com.getir.readingisgood.model.request.PatchBookRequest;
import com.getir.readingisgood.persistence.entity.BookEntity;
import com.getir.readingisgood.persistence.repository.BookRepository;
import com.getir.readingisgood.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    private final ModelMapper modelMapper = new ModelMapper();
    @Mock
    private BookRepository bookRepository;

    private BookService bookService;


    @BeforeEach
    void setup() {
        bookService = new BookServiceImpl(bookRepository, modelMapper);
    }

    @Test
    void should_return_ok_when_save_book() {
        // given
        BookRequest bookRequest = new BookRequest();
        bookRequest.setName("Suc ve Ceza");
        bookRequest.setDescription("Rus edebiyatı");
        bookRequest.setStock(500);

        // when
        BookDto savedBook = bookService.saveBook(bookRequest);

        // then
        assertEquals(savedBook.getStockOfBook(), bookRequest.getStock());
    }

    @Test
    void should_update_stock_when_request_update() {

        // given
        PatchBookRequest request = new PatchBookRequest();
        request.setBookId(10);
        request.setStockOfBook(250);

        BookEntity entity = new BookEntity();
        entity.setId(10L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(bookRepository.save(any())).thenReturn(entity);


        // when
        BookDto savedBook = bookService.updateBook(request);

        // then
        verify(bookRepository, times(1)).findById(entity.getId());
        verify(bookRepository, times(1)).save(entity);
        assertAll("Update Stock",
                () -> assertEquals(savedBook.getStockOfBook(), request.getStockOfBook()),
                () -> assertEquals(entity.getId(), request.getBookId()));
    }


    @Test
    void should_throw_exception_when_find_not_book() {

        // given
        PatchBookRequest request = new PatchBookRequest();
        request.setBookId(10);
        request.setStockOfBook(250);

        when(bookRepository.findById(anyLong())).thenThrow(new BookNotFoundException());

        // when
        BookNotFoundException ex = assertThrows(BookNotFoundException.class, () ->
                bookService.updateBook(request));

        // then
        assertEquals("Book Not Found!", ex.getMessage());

    }
}
