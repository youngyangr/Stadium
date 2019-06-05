package edu.bjtu.demo.controller;

import edu.bjtu.demo.domain.Orders;
import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.service.OrdersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrdersController {
    private final OrdersService OrdersService;

    public OrdersController(OrdersService OrdersService) {
        this.OrdersService = OrdersService;
    }

    @RequestMapping(value = "/kafka", method = RequestMethod.POST)
    public ResponseEntity<Object> reserve(@RequestBody UserCoach usercoach) {
        return send(usercoach,"add");
    }

    @RequestMapping(value = "/kafka", method = RequestMethod.DELETE)
    public ResponseEntity<Object> cancel(@RequestBody UserCoach usercoach) {
        return send(usercoach,"delete");
    }

    private ResponseEntity<Object> send(UserCoach usercoach, String method) {
        UserCoach order = usercoach;
        Orders orders = Orders.builder()
                .userCoach(order)
                .timestamp(System.currentTimeMillis())
                .method(method)
                .build();

        OrdersService.sendOrder(orders);
        return new ResponseEntity("Request send successfully", HttpStatus.OK);
    }

}
