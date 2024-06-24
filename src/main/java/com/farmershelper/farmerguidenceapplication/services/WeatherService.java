package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.clients.WeatherClient;
import com.farmershelper.farmerguidenceapplication.config.WeatherApiConfig;
import com.farmershelper.farmerguidenceapplication.models.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Autowired
    private WeatherApiConfig weatherApiConfig;

    @Autowired
    private WeatherClient weatherClient;

    public WeatherResponse getWeather(String location) {
        return weatherClient.getWeather(weatherApiConfig.getApiKey(), location, "no");
    }
}
