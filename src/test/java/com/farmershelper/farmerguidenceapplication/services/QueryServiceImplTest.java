package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.models.Farmer;
import com.farmershelper.farmerguidenceapplication.models.Query;
import com.farmershelper.farmerguidenceapplication.repository.QueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QueryServiceImplTest {

    @Mock
    private QueryRepository queryRepository;

    @InjectMocks
    private QueryServiceImpl queryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllQueries() {
        queryService.getAllQueries();
        verify(queryRepository, times(1)).findAllByIsDeletedFalse();
    }

    @Test
    void getQueryById() {
        Query query = new Query();
        when(queryRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(query));

        Optional<Query> result = queryService.getQueryById(1L);
        assertTrue(result.isPresent());
        assertEquals(query, result.get());
    }

    @Test
    void saveQuery() {
        Query query = new Query();
        when(queryRepository.save(query)).thenReturn(query);

        Query result = queryService.saveQuery(query);
        assertEquals(query, result);
    }

    @Test
    void updateQuery() {
        Query query = new Query();
        when(queryRepository.save(query)).thenReturn(query);

        Query result = queryService.updateQuery(query);
        assertEquals(query, result);
    }

    @Test
    void deleteQuery() {
        Query query = new Query();
        when(queryRepository.findByIdAndIsDeletedFalse(1L)).thenReturn(Optional.of(query));

        queryService.deleteQuery(1L);
        verify(queryRepository, times(1)).save(query);
    }

    @Test
    void getQueriesByFarmer() {
        Farmer farmer = new Farmer();
        queryService.getQueriesByFarmer(farmer);
        verify(queryRepository, times(1)).findByFarmerAndIsDeletedFalse(farmer);
    }

    @Test
    void getQueriesSortedByCommentStatus() {
        queryService.getQueriesSortedByCommentStatus();
        verify(queryRepository, times(1)).findAllByIsDeletedFalse();
    }
}
