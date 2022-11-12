package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.dto.BookDto;
import com.getir.readingisgood.model.dto.CustomerDto;
import com.getir.readingisgood.model.dto.OrderDto;
import com.getir.readingisgood.model.request.CustomerRequest;
import com.getir.readingisgood.model.response.PageResponse;
import com.getir.readingisgood.service.CustomerService;
import com.getir.readingisgood.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;
    @Mock
    private OrderService orderService;
    @Mock
    private CustomerService customerService;

    @Test
    void should_save_customer_when_request_successfully() {

        // Given
        CustomerRequest customerRequest = CustomerRequest.builder()
                .email("test@test.com")
                .firstname("testName")
                .lastname("testLastName")
                .build();

        BookDto bookDto = BookDto.builder()
                .bookId(1L)
                .bookName("test")
                .build();

        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .books(List.of(bookDto))
                .orderName("test")
                .quantity(100)
                .totalPrice(BigDecimal.TEN)
                .build();

        CustomerDto customerDto = CustomerDto.builder()
                .customerId(1L)
                .orders(Set.of(orderDto))
                .email("test@test.com")
                .firstname("testName")
                .lastname("testLastName")
                .build();

        when(customerService.saveCustomer(customerRequest)).thenReturn(customerDto);

        // When
        ResponseEntity<CustomerDto> getStatus = customerController.saveCustomer(customerRequest);
        // Then
        assertEquals(getStatus.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void should_get_orders_by_customer_id_when_request_successfully() {
        // Given
        long customerId = 1L;
        int next = 1;
        int max = 1;

        BookDto bookDto = BookDto.builder()
                .bookId(1L)
                .bookName("test")
                .build();

        OrderDto orderDto = OrderDto.builder()
                .id(1L)
                .books(List.of(bookDto))
                .orderName("test")
                .quantity(100)
                .totalPrice(BigDecimal.TEN)
                .build();

        PageResponse<OrderDto> pagedOrders = new PageResponse<>();
        pagedOrders.setContentList(Collections.singletonList(orderDto));

        when(orderService.getOrdersByCustomer(customerId, next, max)).thenReturn(pagedOrders);

        // When
        ResponseEntity<?> getStatus = customerController.getOrdersOfCustomerById(1L, max, next);

        // Then
        verify(orderService, times(1)).getOrdersByCustomer(customerId, next, max);
        assertEquals(getStatus.getStatusCode(), HttpStatus.OK);

    }
}
