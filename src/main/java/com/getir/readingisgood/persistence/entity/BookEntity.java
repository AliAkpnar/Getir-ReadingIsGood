package com.getir.readingisgood.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @DecimalMin(value = "0.01", message = "Unit Price must be at least 0.01")
    private BigDecimal unitPrice;
    @Column(name = "stock")
    @Min(value = 0, message = "Stock must be at least 0")
    private int stock;

    @ManyToMany(mappedBy = "books")
    private List<OrderEntity> orders;
}