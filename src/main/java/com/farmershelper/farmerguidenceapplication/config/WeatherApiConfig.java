package com.farmershelper.farmerguidenceapplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherApiConfig {

    @Value("${weatherapi.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
