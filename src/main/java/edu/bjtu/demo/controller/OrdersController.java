package edu.bjtu.demo.controller;

import edu.bjtu.demo.domain.Orders;
import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.service.OrdersService;
import edu.bjtu.demo.service.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private RecordService recordService;

    private final OrdersService OrdersService;

    public OrdersController(OrdersService OrdersService) {
        this.OrdersService = OrdersService;
    }

    @PostMapping
    public Mono<Orders> create(@RequestBody UserCoach usercoach) {
        Orders orders = Orders.builder()
                .userCoach(usercoach)
                .timestamp(System.currentTimeMillis())
                .method("add")
                .delete(false)
                .build();
        OrdersService.sendOrder(orders);
        log.debug("create Orders with orders : {}", orders);
        return recordService.createOrders(orders);
    }

    @DeleteMapping("/{id}")
    public Mono<Boolean> delete(@RequestBody UserCoach usercoach, @PathVariable String id) {
        Orders orders = Orders.builder()
                .userCoach(usercoach)
                .timestamp(System.currentTimeMillis())
                .method("delete")
                .delete(true)
                .build();
        OrdersService.sendOrder(orders);
        log.debug("delete Orders with orders : {}", orders);
        return recordService.delete(id);
    }

    @GetMapping
    public Flux<Orders> findAll() {
        log.debug("findAll Orders");
        return recordService.findAll();
    }

}
