package com.farmershelper.farmerguidenceapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FarmerGuidenceApplication {

    public static void main(String[] args) {

        SpringApplication.run(FarmerGuidenceApplication.class, args);
    }
}
