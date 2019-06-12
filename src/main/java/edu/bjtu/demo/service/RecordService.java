package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Orders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecordService {
    Mono<Orders> createOrders(Orders order);

    Mono<Orders> findOne(String id);

    Flux<Orders> findAll();

    Mono<Boolean> delete(String id);
}
