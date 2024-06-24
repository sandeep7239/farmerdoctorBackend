package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Doctor;
import com.farmershelper.farmerguidenceapplication.models.Role;
import com.farmershelper.farmerguidenceapplication.repository.DoctorRepository;
import com.farmershelper.farmerguidenceapplication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, RoleRepository roleRepository) {
        this.doctorRepository = doctorRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Doctor register(Doctor doctor) {
        if (doctorRepository.findByMobileNumber(doctor.getMobileNumber()).isPresent()) {
            throw new IllegalArgumentException("User already exists. Login again");
        }
        Role doctorRole = roleRepository.findById(2L)
                .orElseThrow(() -> new IllegalArgumentException("Doctor role not found"));
        doctor.setRole(doctorRole);

        return doctorRepository.save(doctor);
    }

    @Override
    public Optional<Doctor> getDoctorByMobileNumber(String mobileNumber) {
        return doctorRepository.findByMobileNumber(mobileNumber);
    }

    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public Doctor saveOtp(Doctor doctor, String otp) {
        doctor.setOtp(otp);
        return doctorRepository.save(doctor);
    }

    @Override
    public boolean validateOtp(Doctor doctor, String otp) {
        return doctor.getOtp().equals(otp);
    }

    @Override
    public void clearOtp(Doctor doctor) {
        doctor.setOtp(null);
        doctorRepository.save(doctor);
    }
}
