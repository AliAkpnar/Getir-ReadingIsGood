package com.getir.readingisgood.converter;

import com.getir.readingisgood.model.dto.OrderDto;
import com.getir.readingisgood.model.response.PageResponse;
import com.getir.readingisgood.persistence.entity.OrderEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderEntityConverter {
    public static OrderDto toMapOrderDto(final OrderEntity orderEntity) {
        return OrderDto.builder()
                .id(orderEntity.getId())
                .quantity(orderEntity.getOrderQuantity())
                .totalPrice(orderEntity.getTotalPrice())
                .orderName(orderEntity.getOrderName())
                .books(orderEntity.getBooks()
                        .stream()
                        .map(BookEntityConverter::toMapBookDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public static PageResponse<OrderDto> toPageableOrderDto(final Page<OrderEntity> orderEntityPage, Integer max) {
        PageResponse<OrderDto> pageResponse = new PageResponse<>();
        List<OrderDto> orderEntityList = orderEntityPage.getContent().stream()
                .map(OrderEntityConverter::toMapOrderDto).collect(Collectors.toList());
        pageResponse.setContentList(orderEntityList);
        pageResponse.setMax(max);
        pageResponse.setNext(orderEntityPage.hasNext() ? orderEntityPage.getNumber() + 1 : null);
        pageResponse.setTotal(orderEntityPage.getTotalElements());
        return pageResponse;
    }
}
