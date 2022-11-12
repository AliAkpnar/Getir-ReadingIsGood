package com.getir.readingisgood.model.request;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {
    @DecimalMin(value = "0.01")
    private BigDecimal unitPrice;
    private String name;
    private String description;
    @Min(value = 0, message = "Book's stock must be positive number!")
    private long stock;
}
