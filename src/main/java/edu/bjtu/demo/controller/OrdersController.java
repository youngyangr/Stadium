package edu.bjtu.demo.controller;

import edu.bjtu.demo.domain.Orders;
import edu.bjtu.demo.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {
    private final OrdersService OrdersService;

    public OrdersController(OrdersService OrdersService) {
        this.OrdersService = OrdersService;
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String Orders() {
        String message = "hello young yang";
        Orders orders = Orders.builder()
            .message(message)
            .timestamp(System.currentTimeMillis())
            .build();

        OrdersService.sendOrder(orders);
        return message;
    }
}
