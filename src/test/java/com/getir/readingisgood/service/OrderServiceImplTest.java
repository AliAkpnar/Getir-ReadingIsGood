package com.getir.readingisgood.service;

import com.getir.readingisgood.advice.exception.OrderQuantityException;
import com.getir.readingisgood.model.dto.OrderDto;
import com.getir.readingisgood.model.dto.StatisticDto;
import com.getir.readingisgood.model.request.BookDetailRequest;
import com.getir.readingisgood.model.request.OrderRequest;
import com.getir.readingisgood.model.response.PageResponse;
import com.getir.readingisgood.persistence.entity.BookEntity;
import com.getir.readingisgood.persistence.entity.CustomerEntity;
import com.getir.readingisgood.persistence.entity.OrderEntity;
import com.getir.readingisgood.persistence.repository.BookRepository;
import com.getir.readingisgood.persistence.repository.CustomerRepository;
import com.getir.readingisgood.persistence.repository.OrderRepository;
import com.getir.readingisgood.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private final ModelMapper modelMapper = new ModelMapper();
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private BookRepository bookRepository;
    private OrderService orderService;

    @BeforeEach
    void setup() {
        orderService = new OrderServiceImpl(orderRepository, customerRepository, bookRepository, modelMapper);
    }

    @Test
    void should_get_all_orders_when_request_with_date_range() {
        // given
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setBooks(Collections.singletonList(bookEntity));

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(30);

        when(orderRepository.findByCreatedDateBetween(startDate, endDate)).thenReturn(Collections.singletonList(orderEntity));
        // when
        List<OrderDto> orderDtoList = orderService.getAllOrders(startDate, endDate);
        // then
        verify(orderRepository, times(1)).findByCreatedDateBetween(startDate, endDate);
        assertEquals(orderDtoList.get(0).getId(), orderEntity.getId());
    }

    @Test
    void should_get_order_when_order_by_id() {
        // given
        long id = 1L;

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setBooks(Collections.singletonList(bookEntity));

        when(orderRepository.findById(id)).thenReturn(Optional.of(orderEntity));
        // when
        OrderDto orderDto = orderService.getOrderById(id);
        // then
        verify(orderRepository, times(1)).findById(id);
        assertEquals(orderDto.getId(), orderEntity.getId());
    }

    @Test
    void should_get_order_by_customer_when_request_with_customer_id() {
        //given
        long customerId = 1L;
        Pageable pageable = Pageable.ofSize(2);

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setBooks(Collections.singletonList(bookEntity));

        Page<OrderEntity> pagedOrders = new PageImpl(Collections.singletonList(orderEntity));


        when(orderRepository.findByCustomerEntityId(1L, pageable.withPage(2))).thenReturn(pagedOrders);
        //when
        PageResponse<OrderDto> ordersByCustomer = orderService.getOrdersByCustomer(customerId, 2, 2);

        //then
        verify(orderRepository, times(1)).findByCustomerEntityId(customerId, pageable.withPage(2));
        assertEquals(ordersByCustomer.getContentList().get(0).getId(), orderEntity.getId());
    }

    @Test
    void should_save_orders_when_request_save_order() {
        //given
        long customerId = 1L;
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setFirstname("testName");
        customerEntity.setLastname("tesLastName");
        customerEntity.setEmail("test@test.com");

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setStock(5);
        bookEntity.setOrders(new ArrayList<>());
        bookEntity.setUnitPrice(new BigDecimal("10.00"));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setBooks(Collections.singletonList(bookEntity));

        BookDetailRequest bookDetailRequest = BookDetailRequest.builder()
                .bookId(1L)
                .quantity(5)
                .build();

        OrderRequest orderRequest = OrderRequest.builder()
                .orderName("test")
                .bookDetailRequestList(Collections.singletonList(bookDetailRequest))
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(modelMapper.map(orderRequest, OrderEntity.class)).thenReturn(orderEntity);
        //when
        OrderDto orderDto = orderService.saveOrders(customerId, orderRequest);
        //then
        assertAll("Save Orders",
                () -> assertNotNull(orderDto),
                () -> assertNotNull(orderDto.getId()),
                () -> assertNotNull(orderDto.getBooks()),
                () -> assertEquals(orderDto.getId(), orderEntity.getId()));
    }

    @Test
    void should_throw_sufficient_quantity_when_request_save_order() {
        //given
        long customerId = 1L;
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setFirstname("testName");
        customerEntity.setLastname("tesLastName");
        customerEntity.setEmail("test@test.com");

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setStock(0);
        bookEntity.setOrders(new ArrayList<>());
        bookEntity.setUnitPrice(new BigDecimal("10.00"));

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setBooks(Collections.singletonList(bookEntity));

        BookDetailRequest bookDetailRequest = BookDetailRequest.builder()
                .bookId(1L)
                .quantity(5)
                .build();

        OrderRequest orderRequest = OrderRequest.builder()
                .orderName("test")
                .bookDetailRequestList(Collections.singletonList(bookDetailRequest))
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerEntity));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(modelMapper.map(orderRequest, OrderEntity.class)).thenReturn(orderEntity);
        //when
        OrderQuantityException ex = assertThrows(OrderQuantityException.class, () ->
                orderService.saveOrders(customerId, orderRequest));

        // then
        assertEquals("Quantity must not be than zero!", ex.getMessage());
    }

    @Test
    void should_get_order_statistic_when_request_monthly_total_orders_statistic() {

        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setOrderQuantity(1);
        orderEntity.setCreatedDate(LocalDateTime.now());
        orderEntity.setUpdatedDate(LocalDateTime.now());
        orderEntity.setTotalPrice(new BigDecimal("50.00"));
        orderEntity.setBooks(Collections.singletonList(bookEntity));

        when(orderRepository.findAll()).thenReturn(Collections.singletonList(orderEntity));

        List<StatisticDto> statisticDto = orderService.getOrderStatistic();

        assertAll("Save Orders",
                () -> assertEquals(LocalDateTime.now().getMonth(), orderEntity.getCreatedDate().getMonth()),
                () -> assertEquals(statisticDto.get(0).getTotalOrderCount(), orderEntity.getOrderQuantity()),
                () -> assertEquals(statisticDto.get(0).getTotalBookCount(), orderEntity.getBooks().size()),
                () -> assertEquals(statisticDto.get(0).getTotalPurchasedAmount(), orderEntity.getTotalPrice()));
    }
}
