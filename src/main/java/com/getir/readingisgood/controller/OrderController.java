package com.getir.readingisgood.controller;

import com.getir.readingisgood.advice.constants.PathConstant;
import com.getir.readingisgood.model.dto.OrderDto;
import com.getir.readingisgood.model.request.OrderRequest;
import com.getir.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController extends BaseController {

    private final OrderService orderService;

    @GetMapping("order")
    public ResponseEntity<List<OrderDto>> getAllOrders(@RequestParam(value = "startDate")
                                                       @DateTimeFormat(pattern = PathConstant.DATE_FORMAT)
                                                       LocalDateTime startDate,
                                                       @RequestParam(value = "endDate")
                                                       @DateTimeFormat(pattern = PathConstant.DATE_FORMAT)
                                                       LocalDateTime endDate) {
        log.info("Get All Orders are started by Date");
        return ResponseEntity.ok(orderService.getAllOrders(startDate, endDate));
    }

    @GetMapping("order/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable(value = "orderId") long orderId) {
        log.info("Get Order is started by order id : {}", orderId);
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping("order/{customerId}")
    public ResponseEntity<OrderDto> saveOrder(@PathVariable(value = "customerId") long customerId,
                                              @Valid @RequestBody OrderRequest orderRequest) {
        log.info("Save Order is started : {}", customerId);
        return ResponseEntity.ok(orderService.saveOrders(customerId, orderRequest));
    }

}
