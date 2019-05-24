package edu.bjtu.demo.configuration;

import edu.bjtu.demo.stream.OrdersStreams;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(OrdersStreams.class)
public class StreamsConfig {
}
