package com.farmershelper.farmerguidenceapplication.services;

import com.farmershelper.farmerguidenceapplication.clients.NewsClient;
import com.farmershelper.farmerguidenceapplication.config.NewsApiConfig;
import com.farmershelper.farmerguidenceapplication.models.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    private NewsApiConfig newsApiConfig;

    @Autowired
    private NewsClient newsClient;

    public NewsResponse getLatestNews() {
        return newsClient.getLatestNews(newsApiConfig.getApiKey());
    }

    public NewsResponse searchNews(String language, String category) {
        return newsClient.searchNews(newsApiConfig.getApiKey(), language, category);
    }
}
