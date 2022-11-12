package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.dto.OrderDto;
import com.getir.readingisgood.model.request.BookDetailRequest;
import com.getir.readingisgood.model.request.OrderRequest;
import com.getir.readingisgood.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderService orderService;

    @Test
    void should_return_ok_when_get_all_orders_by_interval_date() {

        // Given
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(15);

        OrderDto orderDto = getOrderDto();
        when(orderService.getAllOrders(startDate, endDate)).thenReturn(Collections.singletonList(orderDto));

        // When
        ResponseEntity<List<OrderDto>> orders = orderController.getAllOrders(startDate, endDate);

        // Then
        verify(orderService, times(1)).getAllOrders(startDate, endDate);
        assertEquals(orders.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void should_return_ok_when_get_order_by_id() {

        // Given
        long orderId = 1L;

        OrderDto orderDto = getOrderDto();
        when(orderService.getOrderById(orderId)).thenReturn(orderDto);
        // When
        ResponseEntity<OrderDto> orderById = orderController.getOrderById(orderId);

        // Then
        verify(orderService, times(1)).getOrderById(orderId);
        assertEquals(orderById.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void should_return_ok_when_save_order() {

        // Given
        long customerId = 1L;

        BookDetailRequest bookDetailRequest = BookDetailRequest.builder()
                .bookId(1L)
                .quantity(5)
                .build();

        OrderRequest orderRequest = OrderRequest.builder()
                .orderName("test")
                .bookDetailRequestList(Collections.singletonList(bookDetailRequest))
                .build();

        OrderDto orderDto = getOrderDto();
        when(orderService.saveOrders(customerId, orderRequest)).thenReturn(orderDto);
        // When
        ResponseEntity<OrderDto> saveOrder = orderController.saveOrder(customerId, orderRequest);

        // Then
        verify(orderService, times(1)).saveOrders(customerId, orderRequest);
        assertEquals(saveOrder.getStatusCode(), HttpStatus.OK);
    }

    private OrderDto getOrderDto() {
        return OrderDto.builder()
                .id(1L)
                .orderName("test")
                .quantity(100)
                .totalPrice(BigDecimal.TEN)
                .build();
    }
}
