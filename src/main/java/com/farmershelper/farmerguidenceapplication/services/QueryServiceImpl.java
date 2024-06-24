package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Farmer;
import com.farmershelper.farmerguidenceapplication.models.Query;
import com.farmershelper.farmerguidenceapplication.repository.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QueryServiceImpl implements QueryService {

    private final QueryRepository queryRepository;

    @Autowired
    public QueryServiceImpl(QueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public List<Query> getAllQueries() {
        return queryRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Optional<Query> getQueryById(Long id) {
        return queryRepository.findByIdAndIsDeletedFalse(id);
    }

    @Override
    public Query updateQuery(Query query) {
        return queryRepository.save(query);
    }

    @Override
    public Query saveQuery(Query query) {
        return queryRepository.save(query);
    }

    @Override
    public void deleteQuery(Long id) {
        Optional<Query> queryOptional = queryRepository.findByIdAndIsDeletedFalse(id);
        if (queryOptional.isPresent()) {
            Query query = queryOptional.get();
            query.setDeleted(true);
            queryRepository.save(query);
        } else {
            throw new IllegalArgumentException("Query not found");
        }
    }

    @Override
    public List<Query> getQueriesByFarmer(Farmer farmer) {
        return queryRepository.findByFarmerAndIsDeletedFalse(farmer);
    }

    @Override
    public List<Query> getQueriesSortedByCommentStatus() {
        return queryRepository.findAllByIsDeletedFalse()
                .stream()
                .sorted((q1, q2) -> Boolean.compare(q2.getComments().isEmpty(), q1.getComments().isEmpty()))
                .collect(Collectors.toList());
    }
}
