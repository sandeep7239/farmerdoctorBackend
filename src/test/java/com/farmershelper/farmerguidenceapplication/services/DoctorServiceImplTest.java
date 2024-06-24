package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Doctor;
import com.farmershelper.farmerguidenceapplication.models.Role;
import com.farmershelper.farmerguidenceapplication.repository.DoctorRepository;
import com.farmershelper.farmerguidenceapplication.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private DoctorServiceImpl doctorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        Doctor doctor = new Doctor();
        doctor.setMobileNumber("9876543210");

        Role role = new Role();
        role.setId(2L);
        when(roleRepository.findById(2L)).thenReturn(Optional.of(role));
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        Doctor result = doctorService.register(doctor);
        assertEquals(doctor, result);
    }

    @Test
    void getDoctorByMobileNumber() {
        Doctor doctor = new Doctor();
        when(doctorRepository.findByMobileNumber("9876543210")).thenReturn(Optional.of(doctor));

        Optional<Doctor> result = doctorService.getDoctorByMobileNumber("9876543210");
        assertTrue(result.isPresent());
        assertEquals(doctor, result.get());
    }

    @Test
    void generateOtp() {
        String otp = doctorService.generateOtp();
        assertNotNull(otp);
        assertEquals(6, otp.length());
    }

    @Test
    void saveOtp() {
        Doctor doctor = new Doctor();
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        Doctor result = doctorService.saveOtp(doctor, "123456");
        assertEquals("123456", result.getOtp());
    }

    @Test
    void validateOtp() {
        Doctor doctor = new Doctor();
        doctor.setOtp("123456");
        boolean result = doctorService.validateOtp(doctor, "123456");
        assertTrue(result);
    }

    @Test
    void clearOtp() {
        Doctor doctor = new Doctor();
        doctor.setOtp("123456");
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        doctorService.clearOtp(doctor);
        assertNull(doctor.getOtp());
    }
}
