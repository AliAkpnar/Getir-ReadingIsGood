package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.dto.CustomerDto;
import com.getir.readingisgood.model.dto.OrderDto;
import com.getir.readingisgood.model.request.CustomerRequest;
import com.getir.readingisgood.model.response.PageResponse;
import com.getir.readingisgood.service.CustomerService;
import com.getir.readingisgood.service.OrderService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerController extends BaseController {

    private final CustomerService customerService;
    private final OrderService orderService;

    @GetMapping("customer/{customerId}/order")
    public ResponseEntity<PageResponse<OrderDto>> getOrdersOfCustomerById(
            @PathVariable(name = "customerId") long customerId,
            @ApiParam(value = "Max Element") @RequestParam(value = "max", required = false, defaultValue = "20") Integer max,
            @ApiParam(value = "Next Page") @RequestParam(value = "next", required = false, defaultValue = "0") Integer next) {
        log.info("Get Orders Of Customer Id is started : {}", customerId);
        return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId, max, next));
    }

    @PostMapping("customer")
    public ResponseEntity<CustomerDto> saveCustomer(@Validated @RequestBody CustomerRequest customerRequest) {
        log.info("Save Customer is started : {}", customerRequest);
        return ResponseEntity.ok(customerService.saveCustomer(customerRequest));
    }
}
