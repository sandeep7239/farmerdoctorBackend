package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.clients.WeatherClient;
import com.farmershelper.farmerguidenceapplication.config.WeatherApiConfig;
import com.farmershelper.farmerguidenceapplication.models.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private WeatherApiConfig weatherApiConfig;

    @InjectMocks
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWeather() {
        WeatherResponse weatherResponse = new WeatherResponse();
        when(weatherApiConfig.getApiKey()).thenReturn("test_api_key");
        when(weatherClient.getWeather("test_api_key", "New York", "no")).thenReturn(weatherResponse);

        WeatherResponse result = weatherService.getWeather("New York");
        assertEquals(weatherResponse, result);
    }
}
