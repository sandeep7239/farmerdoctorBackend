package com.farmershelper.farmerguidenceapplication.repository;

import com.farmershelper.farmerguidenceapplication.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByMobileNumber(String mobileNumber);
}
