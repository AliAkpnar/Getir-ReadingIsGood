package com.getir.readingisgood.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {
    private Long customerId;
    private String firstname;
    private String lastname;
    private String email;
    private Set<OrderDto> orders;
}
