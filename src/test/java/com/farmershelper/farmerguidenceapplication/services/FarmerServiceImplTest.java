package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Farmer;
import com.farmershelper.farmerguidenceapplication.models.Query;
import com.farmershelper.farmerguidenceapplication.models.Role;
import com.farmershelper.farmerguidenceapplication.repository.FarmerRepository;
import com.farmershelper.farmerguidenceapplication.repository.QueryRepository;
import com.farmershelper.farmerguidenceapplication.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FarmerServiceImplTest {

    @Mock
    private FarmerRepository farmerRepository;

    @Mock
    private QueryRepository queryRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private FarmerServiceImpl farmerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        Farmer farmer = new Farmer();
        farmer.setMobileNumber("9876543210");

        Role role = new Role();
        role.setId(1L);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(farmerRepository.save(farmer)).thenReturn(farmer);

        Farmer result = farmerService.register(farmer);
        assertEquals(farmer, result);
    }

    @Test
    void getFarmerByMobileNumber() {
        Farmer farmer = new Farmer();
        when(farmerRepository.findByMobileNumber("9876543210")).thenReturn(Optional.of(farmer));

        Optional<Farmer> result = farmerService.getFarmerByMobileNumber("9876543210");
        assertTrue(result.isPresent());
        assertEquals(farmer, result.get());
    }

    @Test
    void generateOtp() {
        String otp = farmerService.generateOtp();
        assertNotNull(otp);
        assertEquals(6, otp.length());
    }

    @Test
    void saveOtp() {
        Farmer farmer = new Farmer();
        when(farmerRepository.save(farmer)).thenReturn(farmer);

        Farmer result = farmerService.saveOtp(farmer, "123456");
        assertEquals("123456", result.getOtp());
    }

    @Test
    void validateOtp() {
        Farmer farmer = new Farmer();
        farmer.setOtp("123456");
        boolean result = farmerService.validateOtp(farmer, "123456");
        assertTrue(result);
    }

    @Test
    void clearOtp() {
        Farmer farmer = new Farmer();
        farmer.setOtp("123456");
        when(farmerRepository.save(farmer)).thenReturn(farmer);

        farmerService.clearOtp(farmer);
        assertNull(farmer.getOtp());
    }

    @Test
    void saveQuery() {
        Query query = new Query();
        when(queryRepository.save(query)).thenReturn(query);

        Query result = farmerService.saveQuery(query);
        assertEquals(query, result);
    }

    @Test
    void getQueriesByFarmer() {
        Farmer farmer = new Farmer();
        farmerService.getQueriesByFarmer(farmer);
        verify(queryRepository, times(1)).findByFarmerAndIsDeletedFalse(farmer);
    }
}
