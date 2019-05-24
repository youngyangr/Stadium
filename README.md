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

- ## Restful Service && API @Version

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

- ## Spring stream && kafka

