package com.farmershelper.farmerguidenceapplication.controllers;

import com.farmershelper.farmerguidenceapplication.models.Doctor;
import com.farmershelper.farmerguidenceapplication.services.DoctorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DoctorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
    }

    @Test
    void register() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setMobileNumber("9876543210");
        when(doctorService.register(any(Doctor.class))).thenReturn(doctor);

        mockMvc.perform(post("/api/doctor/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\",\"mobileNumber\":\"9876543210\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void login() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setMobileNumber("9876543210");
        when(doctorService.getDoctorByMobileNumber(anyString())).thenReturn(Optional.of(doctor));
        when(doctorService.generateOtp()).thenReturn("123456");

        mockMvc.perform(post("/api/doctor/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mobileNumber\":\"9876543210\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void validateOtp() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setOtp("123456");
        when(doctorService.getDoctorByMobileNumber(anyString())).thenReturn(Optional.of(doctor));
        when(doctorService.validateOtp(any(Doctor.class), anyString())).thenReturn(true);

        mockMvc.perform(post("/api/doctor/validate-otp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mobileNumber\":\"9876543210\", \"otp\":\"123456\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void logout() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setMobileNumber("9876543210");
        when(doctorService.getDoctorByMobileNumber(anyString())).thenReturn(Optional.of(doctor));

        mockMvc.perform(post("/api/doctor/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mobileNumber\":\"9876543210\"}"))
                .andExpect(status().isOk());
    }
}
