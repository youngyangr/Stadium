package edu.bjtu.demo.repository;

import edu.bjtu.demo.domain.Orders;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface OrdersRepository extends ReactiveMongoRepository<Orders, String> {
    Mono<Orders> findByIdAndDeleteIsFalse(String id);
}
