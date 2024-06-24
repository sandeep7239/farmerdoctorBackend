package com.farmershelper.farmerguidenceapplication.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Request.Options options() {
        return new Request.Options(10000, 60000);
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(2000, 4000, 5);
    }
}
