package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Farmer;
import com.farmershelper.farmerguidenceapplication.models.Query;
import com.farmershelper.farmerguidenceapplication.models.Role;
import com.farmershelper.farmerguidenceapplication.repository.FarmerRepository;
import com.farmershelper.farmerguidenceapplication.repository.QueryRepository;
import com.farmershelper.farmerguidenceapplication.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class FarmerServiceImpl implements FarmerService {

    private final FarmerRepository farmerRepository;
    private final QueryRepository queryRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public FarmerServiceImpl(FarmerRepository farmerRepository, QueryRepository queryRepository, RoleRepository roleRepository) {
        this.farmerRepository = farmerRepository;
        this.queryRepository = queryRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Farmer register(Farmer farmer) {
        if (farmerRepository.findByMobileNumber(farmer.getMobileNumber()).isPresent()) {
            throw new IllegalArgumentException("User already exists. Login again");
        }

        Role farmerRole = roleRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Farmer role not found"));
        farmer.setRole(farmerRole);

        return farmerRepository.save(farmer);
    }

    @Override
    public Optional<Farmer> getFarmerByMobileNumber(String mobileNumber) {
        return farmerRepository.findByMobileNumber(mobileNumber);
    }

    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public Farmer saveOtp(Farmer farmer, String otp) {
        farmer.setOtp(otp);
        return farmerRepository.save(farmer);
    }

    @Override
    public boolean validateOtp(Farmer farmer, String otp) {
        return farmer.getOtp().equals(otp);
    }

    @Override
    public void clearOtp(Farmer farmer) {
        farmer.setOtp(null);
        farmerRepository.save(farmer);
    }

    @Override
    public Query saveQuery(Query query) {
        return queryRepository.save(query);
    }

    @Override
    public List<Query> getQueriesByFarmer(Farmer farmer) {
        return queryRepository.findByFarmerAndIsDeletedFalse(farmer);
    }

    @Override
    public Query updateQuery(Long queryId, Query queryDetails) {
        Optional<Query> queryOptional = queryRepository.findByIdAndIsDeletedFalse(queryId);
        if (queryOptional.isPresent()) {
            Query query = queryOptional.get();
            query.setDescription(queryDetails.getDescription());
            return queryRepository.save(query);
        } else {
            throw new IllegalArgumentException("Query not found");
        }
    }

    @Override
    public void deleteQuery(Long queryId) {
        Optional<Query> queryOptional = queryRepository.findByIdAndIsDeletedFalse(queryId);
        if (queryOptional.isPresent()) {
            Query query = queryOptional.get();
            query.setDeleted(true);
            queryRepository.save(query);
        } else {
            throw new IllegalArgumentException("Query not found");
        }
    }
}
