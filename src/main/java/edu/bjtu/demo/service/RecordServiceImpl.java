package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Orders;
import edu.bjtu.demo.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public Mono<Orders> createOrders(Orders order) {
        return ordersRepository.insert(order);
    }

    @Override
    public Mono<Orders> findOne(String id) {
        return ordersRepository.findByIdAndDeleteIsFalse(id).
                switchIfEmpty(Mono.error(new Exception("No Order found with Id: " + id)));
    }

    @Override
    public Flux<Orders> findAll() {
        return ordersRepository.findAll();
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return findOne(id).doOnSuccess(order -> {
            order.setDelete(true);
            ordersRepository.save(order).subscribe();
        }).flatMap(order -> Mono.just(Boolean.TRUE));
    }
}
