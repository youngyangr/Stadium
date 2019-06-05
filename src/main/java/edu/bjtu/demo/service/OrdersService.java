package edu.bjtu.demo.service;

import edu.bjtu.demo.domain.Orders;
import edu.bjtu.demo.stream.OrdersStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
@Slf4j
public class OrdersService {
    private final OrdersStreams ordersStreams;

    public OrdersService(OrdersStreams ordersStreams) {
        this.ordersStreams = ordersStreams;
    }

    public void sendOrder(final Orders orders) {
        MessageChannel messageChannel = ordersStreams.outboundOrders();
        messageChannel.send(MessageBuilder
                .withPayload(orders)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }
}
