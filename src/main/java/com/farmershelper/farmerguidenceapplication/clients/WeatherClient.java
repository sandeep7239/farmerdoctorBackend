package com.farmershelper.farmerguidenceapplication.clients;

import com.farmershelper.farmerguidenceapplication.models.WeatherResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherClient", url = "${weatherapi.url}")
public interface WeatherClient {
    @GetMapping("/current.json")
    WeatherResponse getWeather(@RequestParam("key") String apiKey,
                               @RequestParam("q") String location,
                               @RequestParam("aqi") String aqi);
}