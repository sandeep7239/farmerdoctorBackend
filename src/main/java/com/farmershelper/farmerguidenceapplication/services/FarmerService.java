package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Farmer;
import com.farmershelper.farmerguidenceapplication.models.Query;

import java.util.List;
import java.util.Optional;

public interface FarmerService {
    Farmer register(Farmer farmer);

    Optional<Farmer> getFarmerByMobileNumber(String mobileNumber);

    String generateOtp();

    Farmer saveOtp(Farmer farmer, String otp);

    boolean validateOtp(Farmer farmer, String otp);

    void clearOtp(Farmer farmer);

    Query saveQuery(Query query);

    List<Query> getQueriesByFarmer(Farmer farmer);

    Query updateQuery(Long queryId, Query queryDetails);

    void deleteQuery(Long queryId);
}
