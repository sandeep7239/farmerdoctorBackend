package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.clients.NewsClient;
import com.farmershelper.farmerguidenceapplication.config.NewsApiConfig;
import com.farmershelper.farmerguidenceapplication.models.NewsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class NewsServiceTest {

    @Mock
    private NewsClient newsClient;

    @Mock
    private NewsApiConfig newsApiConfig;

    @InjectMocks
    private NewsService newsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLatestNews() {
        NewsResponse newsResponse = new NewsResponse();
        when(newsApiConfig.getApiKey()).thenReturn("test_api_key");
        when(newsClient.getLatestNews("test_api_key")).thenReturn(newsResponse);

        NewsResponse result = newsService.getLatestNews();
        assertEquals(newsResponse, result);
    }

    @Test
    void searchNews() {
        NewsResponse newsResponse = new NewsResponse();
        when(newsApiConfig.getApiKey()).thenReturn("test_api_key");
        when(newsClient.searchNews("test_api_key", "en", "technology")).thenReturn(newsResponse);

        NewsResponse result = newsService.searchNews("en", "technology");
        assertEquals(newsResponse, result);
    }
}
