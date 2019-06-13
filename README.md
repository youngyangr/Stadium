# Assignment

---

- ## Basic Functionality
- Sign in
- Sign up
 ![image](https://github.com/youngyangor/assignment/blob/master/image/entrance.png?raw=true)
- Browse trainers
 ![image](https://github.com/youngyangor/assignment/blob/master/image/coach-list.png?raw=true)

---
- ## Database Entity–relationship model
<img src="https://github.com/youngyangor/assignment/blob/master/image/ER.png?raw=true" style="content:80%"/>
 
---

- ## JPA Feature
- JPA Pagination
- JPA Auditing
- JPA Multi table query
```
@Query(value = "SELECT NEW edu.bjtu.demo.domain.Coach(c.id, c.name, c.subject) from User as u, Coach as c, UserCoach as uc"
        + " where u.id = uc.userId and c.id = uc.coachId and c.subject = ?1 and u.id = ?2")
Page<Coach> getBySubject(String subject, Integer id, Pageable pageable);

@Query(value = "SELECT NEW edu.bjtu.demo.domain.Coach(c.id,c.name,c.subject) from User as u, Coach as c, UserCoach as uc"
        + " where u.id = uc.userId and c.id = uc.coachId and u.id = ?1")
Page<Coach> getAllCoaches(Integer id, Pageable pageable);

@Query(value = "SELECT NEW edu.bjtu.demo.domain.Coach(c.id, c.name, c.subject) from User as u, Coach as c, UserCoach as uc"
        + " where u.id = uc.userId and c.id = uc.coachId and c.subject = ?1 and u.id = ?2")
Iterable<Coach> getBySubject(String subject, Integer id);

@Query(value = "SELECT NEW edu.bjtu.demo.domain.Coach(c.id,c.name,c.subject) from User as u, Coach as c, UserCoach as uc"
        + " where u.id = uc.userId and c.id = uc.coachId and u.id = ?1")
Iterable<Coach> getAllCoaches(Integer id);
```
![gif](https://github.com/youngyangor/assignment/blob/master/image/query.gif?raw=true)

---

- ## Web Caching 
- **@EnableCaching** annotation
- **@Cacheable** annotation
- **@CacheEvict** annotation
```
@Cacheable(value = "all")
public Page<Coach> cacheAllCoach(Page<Coach> coachList){
    return coachList;
}

@CacheEvict(value = "all")
@RequestMapping("/evict")
public String evictAllCoach() {
    log.info("Evict all coaches");
    return "redirect:/home";
}
```

---

- ## Restful Service & API Version
- **@Version** annotation
- **@RestController** annotation
- **@RequestMapping("/api")** 
```
@RestController
@RequestMapping("/api")
@Api(tags = "Restful interface", description = "Provide some restful API")
```

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
```
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.bjtu.demo.controller"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaData());
    }
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("REST API for Online Store")
                .description("\"Spring Boot REST API for Online Store\"")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("API Helper","https://github.com/youngyangor", "16301162@bjtu.edu.cn"))
                .build();
    }
}
```

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

- **Functional request handler**
```
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

    public Mono<ServerResponse> save(ServerRequest request) {...}

    public Mono<ServerResponse> delete(ServerRequest request) {...}
}
```

- **Reactive persisting data**
```
public interface OrdersRepository extends ReactiveMongoRepository<Orders, String> {
    Mono<Orders> findByIdAndDeleteIsFalse(String id);
}
```