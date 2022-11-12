package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.advice.exception.BookNotFoundException;
import com.getir.readingisgood.advice.exception.CustomerNotFoundException;
import com.getir.readingisgood.advice.exception.OrderNotFoundException;
import com.getir.readingisgood.advice.exception.OrderQuantityException;
import com.getir.readingisgood.converter.OrderEntityConverter;
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
import com.getir.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDto> getAllOrders(LocalDateTime startDate, LocalDateTime endDate) {
        List<OrderEntity> orderEntities = orderRepository
                .findByCreatedDateBetween(startDate, endDate);

        return orderEntities.stream()
                .map(OrderEntityConverter::toMapOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        return OrderEntityConverter.toMapOrderDto(orderEntity);
    }

    @Override
    public List<StatisticDto> getOrderStatistic() {

        List<OrderEntity> orderEntityList = orderRepository.findAll();
        Map<Month, List<OrderEntity>> monthOrderEntityMap = orderEntityList.stream()
                .collect(groupingBy(order -> order.getCreatedDate().getMonth()));

        List<StatisticDto> statisticDtoList = new ArrayList<>();

        for (Map.Entry<Month, List<OrderEntity>> entry : monthOrderEntityMap.entrySet()) {
            StatisticDto statisticDto = new StatisticDto();
            statisticDto.setMonth(entry.getKey().name());

            List<OrderEntity> orderEntityByMonthList = entry.getValue();

            int totalBook = orderEntityByMonthList.stream().map(OrderEntity::getOrderQuantity)
                    .reduce(0, Integer::sum);

            BigDecimal totalPrice = orderEntityByMonthList.stream()
                    .map(OrderEntity::getTotalPrice)
                    .reduce(BigDecimal.valueOf(0), BigDecimal::add);

            statisticDto.setTotalOrderCount(orderEntityByMonthList.size());
            statisticDto.setTotalBookCount(totalBook);
            statisticDto.setTotalPurchasedAmount(totalPrice);

            statisticDtoList.add(statisticDto);
        }
        return statisticDtoList;
    }

    @Override
    @Transactional
    public OrderDto saveOrders(long customerId, OrderRequest orderRequest) {
        OrderEntity orderEntity = mapToEntity(orderRequest);

        CustomerEntity customerEntity = customerRepository
                .findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        List<BookDetailRequest> bookDetailRequestList = orderRequest.getBookDetailRequestList();

        List<BookEntity> bookEntityList = new ArrayList<>();
        int totalBookCount = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (BookDetailRequest bookDetailRequest : bookDetailRequestList) {
            BookEntity bookEntity = bookRepository.findById(bookDetailRequest.getBookId()).orElseThrow(BookNotFoundException::new);
            bookEntityList.add(bookEntity);
            int quantity = bookDetailRequest.getQuantity();
            totalBookCount += quantity;
            stockUpdate(quantity, bookEntity);
            BigDecimal price = bookEntity.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
            totalPrice = price.add(totalPrice);
            orderEntity.setCustomerEntity(customerEntity);
            bookEntity.getOrders().add(orderEntity);
        }
        orderEntity.setOrderQuantity(totalBookCount);
        orderEntity.setTotalPrice(totalPrice);
        orderEntity.setBooks(bookEntityList);
        orderRepository.save(orderEntity);
        log.info("Order is saved : {}", orderEntity.getId());
        return OrderEntityConverter.toMapOrderDto(orderEntity);
    }

    @Override
    public PageResponse<OrderDto> getOrdersByCustomer(Long customerId, Integer max, Integer next) {
        Page<OrderEntity> orderEntityPage = orderRepository.findByCustomerEntityId(customerId, PageRequest.of(next, max));
        return OrderEntityConverter.toPageableOrderDto(orderEntityPage, max);
    }

    private void stockUpdate(int quantity, BookEntity bookEntity) {
        int stock = bookEntity.getStock();
        log.info("Stock is : {} ", stock);
        int remainingStock = stock - quantity;
        if (remainingStock < 0) {
            throw new OrderQuantityException();
        }
        bookEntity.setStock(remainingStock);
    }

    private OrderEntity mapToEntity(OrderRequest orderRequest) {
        return modelMapper.map(orderRequest, OrderEntity.class);
    }
}
