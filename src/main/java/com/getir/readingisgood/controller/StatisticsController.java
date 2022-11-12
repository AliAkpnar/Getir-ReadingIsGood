package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.dto.StatisticDto;
import com.getir.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatisticsController extends BaseController {

    private final OrderService orderService;

    @GetMapping("statistics")
    public ResponseEntity<List<StatisticDto>> getStatisticsByMonth() {
        log.info("Get Statistics by Month is started");
        return ResponseEntity.ok(orderService.getOrderStatistic());
    }
}
