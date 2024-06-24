package com.farmershelper.farmerguidenceapplication.repository;

import com.farmershelper.farmerguidenceapplication.models.Query;
import com.farmershelper.farmerguidenceapplication.models.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {
    List<Query> findByFarmerAndIsDeletedFalse(Farmer farmer);
    Optional<Query> findByIdAndIsDeletedFalse(Long id);
    List<Query> findAllByIsDeletedFalse();
}
