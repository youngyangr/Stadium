package edu.bjtu.demo.configuration;

import edu.bjtu.demo.controller.OrdersHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@ComponentScan(basePackages = { "edu.bjtu.demo.controller"})
public class RoutesConfiguration {
    @Autowired
    private OrdersHandler ordersHandler;

    @Bean
    public RouterFunction<ServerResponse> ordersRoute() {
        return route(GET("/orders"), ordersHandler::findAll)
                .andRoute(POST("/orders"), ordersHandler::save)
                .andRoute(DELETE("/orders/{id}"), ordersHandler::delete);
    }

}