package com.getir.readingisgood.service;

import com.getir.readingisgood.model.dto.OrderDto;
import com.getir.readingisgood.model.dto.StatisticDto;
import com.getir.readingisgood.model.request.OrderRequest;
import com.getir.readingisgood.model.response.PageResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders(LocalDateTime startDate, LocalDateTime endDate);

    List<StatisticDto> getOrderStatistic();

    OrderDto getOrderById(long orderId);

    OrderDto saveOrders(long customerId, OrderRequest orderRequest);

    PageResponse<OrderDto> getOrdersByCustomer(Long customerId, Integer max, Integer next);

}
