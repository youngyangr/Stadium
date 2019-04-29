package edu.bjtu.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class UserAuditorAware implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        //TODO: 根据实际情况取真实用户
        return Optional.of("admin");
    }
}
