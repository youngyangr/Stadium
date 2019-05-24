package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Orders;
import edu.bjtu.demo.stream.OrdersStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrdersListener {
    @StreamListener(OrdersStreams.INPUT)
    public void handleOrders(@Payload Orders orders) {
        System.out.print("Received orders: {}" + orders);
        log.info("Received orders: {}", orders);
    }
}
