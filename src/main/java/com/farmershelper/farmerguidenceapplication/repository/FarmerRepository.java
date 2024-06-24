package com.farmershelper.farmerguidenceapplication.repository;

import com.farmershelper.farmerguidenceapplication.models.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Optional<Farmer> findByMobileNumber(String mobileNumber);
}
