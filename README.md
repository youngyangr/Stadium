# Assignment

---

- ## Basic Functionality
- Sign in
- Sign up
 ![image](https://github.com/youngyangor/assignment/blob/master/image/entrance.png?raw=true)
- Browse trainers
 ![image](https://github.com/youngyangor/assignment/blob/master/image/coach-list.png?raw=true)

---

- ## JPA Feature
- JPA Pagination
- JPA Auditing
- JPA Multi table query
![gif](https://github.com/youngyangor/assignment/blob/master/image/query.gif?raw=true)

---

- ## Web Caching 
- **@EnableCaching** annotation
- **@Cacheable** annotation
- **@CacheEvict** annotation

---

- ## Restful Service && API Version
- **@Version** annotation
- **@RestController** annotation
- **@RequestMapping("/api")** 

---

- ## Rate limit
- **Google Guava RateLimiter**
- **Nginx Rate limit**
```
#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;
#pid        logs/nginx.pid;

events {
    use epoll;
    worker_connections  1024;
}

http {
    include       mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';
    
    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    keepalive_timeout  65;

    #gzip  on;


    # 定义了一个 mylimit 缓冲区，请求频率为每秒 1 个请求（nr/s）
    limit_req_zone $binary_remote_addr zone=mylimit:10m rate=1r/s;

    server {
    	listen  80;
        server_name  localhost:8080;

	location = / {
	    proxy_pass http://localhost:8080;
	}

	location ^~ /static/ {
	    root /src/main/resources/static/;
	}

    	location / {
		limit_req zone=mylimit burst=3 nodelay;
		proxy_pass  http://localhost:8080/;  
    	}
    }

}
```

---

- ## Oauth2 Authentication
 ![image](https://github.com/youngyangor/assignment/blob/master/image/postman-1.png?raw=true)
- obtain access token and request again
 ![image](https://github.com/youngyangor/assignment/blob/master/image/postman-2.png?raw=true)

---

- ## Online API document (Swagger)
![image](https://github.com/youngyangor/assignment/blob/master/image/swagger.png?raw=true)

---

- ## Kafka Configuration
```
spring:
  cloud:
    stream:
      kafka:
        binder:
          brokers: localhost:9092
      bindings:
        orders-in:
          destination: orders
          contentType: application/json
        orders-out:
          destination: orders
          contentType: application/json  
```

---

- ## Spring cloud stream
```
public interface OrdersStreams {
    String INPUT = "orders-in";
    String OUTPUT = "orders-out";

    @Input(INPUT)
    SubscribableChannel inboundOrders();

    @Output(OUTPUT)
    MessageChannel outboundOrders();
}
```

---

- ## Spring mvc refactored to Spring WebFlux
- **Spring Webflux restful API**
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-webflux</artifactId>
</dependency>
```

- **Functional request handler**
```
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
```

- **Reactive persisting data **
```
public interface OrdersRepository extends ReactiveMongoRepository<Orders, String> {
    Mono<Orders> findByIdAndDeleteIsFalse(String id);
}
```