package com.farmershelper.farmerguidenceapplication.controllers;

import com.farmershelper.farmerguidenceapplication.models.NewsResponse;
import com.farmershelper.farmerguidenceapplication.services.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NewsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NewsService newsService;

    @InjectMocks
    private NewsController newsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();
    }

    @Test
    void getLatestNews() throws Exception {
        NewsResponse newsResponse = new NewsResponse();
        when(newsService.getLatestNews()).thenReturn(newsResponse);

        mockMvc.perform(get("/api/news/latest")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void searchNews() throws Exception {
        NewsResponse newsResponse = new NewsResponse();
        when(newsService.searchNews("en", "technology")).thenReturn(newsResponse);

        mockMvc.perform(get("/api/news/search")
                        .param("language", "en")
                        .param("category", "technology")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
