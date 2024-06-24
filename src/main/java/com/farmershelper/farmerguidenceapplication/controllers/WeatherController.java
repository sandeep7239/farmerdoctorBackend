package com.farmershelper.farmerguidenceapplication.controllers;

import com.farmershelper.farmerguidenceapplication.models.WeatherResponse;
import com.farmershelper.farmerguidenceapplication.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:3000")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String location) {
        try {
            WeatherResponse response = weatherService.getWeather(location);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
