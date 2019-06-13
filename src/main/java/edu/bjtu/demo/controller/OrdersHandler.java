package edu.bjtu.demo.controller;

import edu.bjtu.demo.domain.Orders;
import edu.bjtu.demo.domain.UserCoach;
import edu.bjtu.demo.service.OrdersService;
import edu.bjtu.demo.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class OrdersHandler {
    @Autowired
    private RecordService recordService;

    private final OrdersService OrdersService;

    public OrdersHandler(OrdersService ordersService) {
        OrdersService = ordersService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(recordService.findAll(), Orders.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        final Mono<UserCoach> usercoach = request.bodyToMono(UserCoach.class);
        UserCoach uc = new UserCoach();
        uc.setUserId(1);//user id of usercoach
        uc.setCoachId(1);//coach id of usercoach
        Orders orders = Orders.builder()
                .userCoach(uc)
                .timestamp(System.currentTimeMillis())
                .method("add")
                .delete(false)
                .build();
        OrdersService.sendOrder(orders);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(recordService.createOrders(orders), Orders.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        final Mono<UserCoach> usercoach = request.bodyToMono(UserCoach.class);
        UserCoach uc = new UserCoach();
        uc.setUserId(1);//user id of usercoach
        uc.setCoachId(1);//coach id of usercoach
        Orders orders = Orders.builder()
                .userCoach(uc)
                .timestamp(System.currentTimeMillis())
                .method("delete")
                .delete(true)
                .build();
        OrdersService.sendOrder(orders);
        String id = request.pathVariable("id");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(recordService.delete(id), boolean.class);
    }
}