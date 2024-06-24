package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Farmer;
import com.farmershelper.farmerguidenceapplication.models.Query;

import java.util.List;
import java.util.Optional;

public interface QueryService {
    List<Query> getAllQueries();
    Optional<Query> getQueryById(Long id);
    Query updateQuery(Query query);
    Query saveQuery(Query query);
    void deleteQuery(Long id);
    List<Query> getQueriesByFarmer(Farmer farmter);
    List<Query> getQueriesSortedByCommentStatus();
}
