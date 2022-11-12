package com.getir.readingisgood.converter;

import com.getir.readingisgood.model.dto.CustomerDto;
import com.getir.readingisgood.persistence.entity.CustomerEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerEntityConverter {
    public static CustomerDto toMapCustomerDto(final CustomerEntity customerEntity) {
        return CustomerDto.builder()
                .customerId(customerEntity.getId())
                .firstname(customerEntity.getFirstname())
                .lastname(customerEntity.getLastname())
                .email(customerEntity.getEmail())
                .orders(CollectionUtils.isEmpty(customerEntity.getOrders()) ? null : customerEntity.getOrders()
                        .stream()
                        .map(OrderEntityConverter::toMapOrderDto)
                        .collect(Collectors.toSet()))
                .build();
    }
}
