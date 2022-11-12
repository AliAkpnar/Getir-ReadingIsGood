package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.dto.BookDto;
import com.getir.readingisgood.model.dto.OrderDto;
import com.getir.readingisgood.model.request.BookRequest;
import com.getir.readingisgood.model.request.PatchBookRequest;
import com.getir.readingisgood.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Test
    void should_return_ok_when_request_successfully_for_save() {
        // Given
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setQuantity(5);
        orderDto.setTotalPrice(BigDecimal.TEN);

        BookRequest bookRequest = BookRequest.builder()
                .name("test")
                .stock(1L)
                .description("test")
                .unitPrice(new BigDecimal("10.00"))
                .build();

        BookDto bookDto = BookDto.builder()
                .bookId(1L)
                .stockOfBook(5)
                .bookName("test")
                .build();

        when(bookService.saveBook(bookRequest)).thenReturn(bookDto);

        // When
        ResponseEntity<BookDto> status = bookController.saveBook(bookRequest);

        // Then
        verify(bookService).saveBook(bookRequest);
        assertEquals(status.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void should_return_ok_when_request_successfully_for_update() {
        // Given
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setQuantity(5);
        orderDto.setTotalPrice(BigDecimal.TEN);

        BookDto bookDto = BookDto.builder()
                .bookId(1L)
                .stockOfBook(5)
                .bookName("test")
                .build();

        PatchBookRequest patchBookRequest = PatchBookRequest.builder()
                .bookId(1L)
                .stockOfBook(5)
                .build();

        when(bookService.updateBook(patchBookRequest)).thenReturn(bookDto);

        // When
        ResponseEntity<BookDto> status = bookController.updateBook(patchBookRequest);

        // Then
        verify(bookService, times(1)).updateBook(patchBookRequest);
        assertEquals(status.getStatusCode(), HttpStatus.OK);
    }
}
