package edu.bjtu.demo;

import edu.bjtu.demo.configuration.UserAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableCaching
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableReactiveMongoRepositories
@SpringBootApplication
public class AssignmentApplication {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new UserAuditorAware();
    }

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

}
