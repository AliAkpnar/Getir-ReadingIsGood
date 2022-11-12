package com.getir.readingisgood.service.impl;

import com.getir.readingisgood.advice.exception.CustomerAlreadyExistsException;
import com.getir.readingisgood.converter.CustomerEntityConverter;
import com.getir.readingisgood.model.dto.CustomerDto;
import com.getir.readingisgood.model.request.CustomerRequest;
import com.getir.readingisgood.persistence.entity.CustomerEntity;
import com.getir.readingisgood.persistence.repository.CustomerRepository;
import com.getir.readingisgood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;

    @Override
    public CustomerDto saveCustomer(CustomerRequest customerRequest) {
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new CustomerAlreadyExistsException();
        }

        CustomerEntity customer = mapToEntity(customerRequest);
        customerRepository.save(customer);
        log.info("Customer saved : {}", customerRequest.getEmail());
        return CustomerEntityConverter.toMapCustomerDto(customer);
    }

    private CustomerEntity mapToEntity(CustomerRequest customerRequest) {
        return modelMapper
                .map(customerRequest, CustomerEntity.class);
    }

}
