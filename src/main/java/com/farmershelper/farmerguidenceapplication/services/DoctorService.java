package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Doctor;

import java.util.Optional;

public interface DoctorService {
    Doctor register(Doctor doctor);
    Optional<Doctor> getDoctorByMobileNumber(String mobileNumber);
    String generateOtp();
    Doctor saveOtp(Doctor doctor, String otp);
    boolean validateOtp(Doctor doctor, String otp);
    void clearOtp(Doctor doctor);
}
