package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.dto.StatisticDto;
import com.getir.readingisgood.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatisticsControllerTest {

    @InjectMocks
    private StatisticsController statisticsController;
    @Mock
    private OrderService orderService;


    @Test
    void should_return_ok_when_get_statistic_by_month() {

        //Given

        StatisticDto statisticDto = StatisticDto.builder()
                .totalBookCount(10)
                .totalOrderCount(5)
                .totalPurchasedAmount(new BigDecimal("50.00"))
                .month(String.valueOf(LocalDateTime.now()))
                .build();

        when(orderService.getOrderStatistic()).thenReturn(Collections.singletonList(statisticDto));

        //When
        ResponseEntity<List<StatisticDto>> statisticsByMonth = statisticsController.getStatisticsByMonth();

        //Then
        verify(orderService, times(1)).getOrderStatistic();
        assertEquals(statisticsByMonth.getStatusCode(), HttpStatus.OK);
    }
}
