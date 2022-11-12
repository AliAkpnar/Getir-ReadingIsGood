package com.getir.readingisgood.service;

import com.getir.readingisgood.model.dto.CustomerDto;
import com.getir.readingisgood.model.request.CustomerRequest;

public interface CustomerService {
    CustomerDto saveCustomer(CustomerRequest customer);
}
