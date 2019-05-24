package edu.bjtu.demo.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrdersStreams {
    String INPUT = "orders-in";
    String OUTPUT = "orders-out";

    @Input(INPUT)
    SubscribableChannel inboundOrders();

    @Output(OUTPUT)
    MessageChannel outboundOrders();
}
