package com.getir.readingisgood.service;

import com.getir.readingisgood.advice.exception.CustomerAlreadyExistsException;
import com.getir.readingisgood.model.dto.CustomerDto;
import com.getir.readingisgood.model.request.CustomerRequest;
import com.getir.readingisgood.persistence.repository.CustomerRepository;
import com.getir.readingisgood.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    private final ModelMapper modelMapper = new ModelMapper();
    @Mock
    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setup() {
        customerService = new CustomerServiceImpl(customerRepository, modelMapper);
        getCustomerRequest();
    }

    @Test
    void should_throw_exception_when_customer_exist_with_same_email() {

        // given
        when(customerRepository.existsByEmail(anyString())).thenReturn(true);

        // when
        CustomerAlreadyExistsException ex = assertThrows(CustomerAlreadyExistsException.class, () ->
                customerService.saveCustomer(getCustomerRequest()));

        // then
        assertEquals("Customer already exists!", ex.getMessage());
    }


    @Test
    void should_return_ok_when_save_customer() {
        // given
        when(customerRepository.existsByEmail(anyString())).thenReturn(false);

        // when
        CustomerDto dto = customerService.saveCustomer(getCustomerRequest());

        // then
        assertAll("Save Customer",
                () -> assertEquals(dto.getEmail(), getCustomerRequest().getEmail()),
                () -> assertEquals(dto.getFirstname(), getCustomerRequest().getFirstname()),
                () -> assertEquals(dto.getLastname(), getCustomerRequest().getLastname()));
    }

    private CustomerRequest getCustomerRequest() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setEmail("temp@temp.com");
        customerRequest.setFirstname("TestName");
        customerRequest.setLastname("TestLastName");
        return customerRequest;
    }
}
