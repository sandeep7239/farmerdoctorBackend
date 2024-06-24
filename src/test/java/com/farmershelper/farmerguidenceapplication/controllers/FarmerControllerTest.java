package com.farmershelper.farmerguidenceapplication.controllers;

import com.farmershelper.farmerguidenceapplication.models.Farmer;
import com.farmershelper.farmerguidenceapplication.services.FarmerService;
import com.farmershelper.farmerguidenceapplication.services.QueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FarmerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FarmerService farmerService;

    @Mock
    private QueryService queryService;

    @InjectMocks
    private FarmerController farmerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(farmerController).build();
    }

    @Test
    void register() throws Exception {
        Farmer farmer = new Farmer();
        farmer.setMobileNumber("9876543210");
        when(farmerService.register(any(Farmer.class))).thenReturn(farmer);

        mockMvc.perform(post("/api/farmer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"mobileNumber\":\"9876543210\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void login() throws Exception {
        Farmer farmer = new Farmer();
        farmer.setMobileNumber("9876543210");
        when(farmerService.getFarmerByMobileNumber(anyString())).thenReturn(Optional.of(farmer));
        when(farmerService.generateOtp()).thenReturn("123456");

        mockMvc.perform(post("/api/farmer/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mobileNumber\":\"9876543210\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void validateOtp() throws Exception {
        Farmer farmer = new Farmer();
        farmer.setOtp("123456");
        when(farmerService.getFarmerByMobileNumber(anyString())).thenReturn(Optional.of(farmer));
        when(farmerService.validateOtp(any(Farmer.class), anyString())).thenReturn(true);

        mockMvc.perform(post("/api/farmer/validate-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mobileNumber\":\"9876543210\", \"otp\":\"123456\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void logout() throws Exception {
        Farmer farmer = new Farmer();
        farmer.setMobileNumber("9876543210");
        when(farmerService.getFarmerByMobileNumber(anyString())).thenReturn(Optional.of(farmer));

        mockMvc.perform(post("/api/farmer/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mobileNumber\":\"9876543210\"}"))
                .andExpect(status().isOk());
    }
}
