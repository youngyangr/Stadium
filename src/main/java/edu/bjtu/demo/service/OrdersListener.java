package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Orders;
import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.stream.OrdersStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrdersListener {

    private ReserveService reserveService;

    @Autowired
    public void setReserveService(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @StreamListener(OrdersStreams.INPUT)
    public void handleOrders(@Payload Orders orders) {
        UserCoach usercoach = orders.getUserCoach();
        String method = orders.getMethod();
        if(method.equals("add"))
            this.reserveService.reserve(usercoach);
        else if(method.equals("delete"))
            this.reserveService.cancel(usercoach);
    }
}
