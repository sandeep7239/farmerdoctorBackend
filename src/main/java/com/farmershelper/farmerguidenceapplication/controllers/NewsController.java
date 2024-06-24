package com.farmershelper.farmerguidenceapplication.controllers;

import com.farmershelper.farmerguidenceapplication.models.NewsResponse;
import com.farmershelper.farmerguidenceapplication.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "http://localhost:3000")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/latest")
    public ResponseEntity<NewsResponse> getLatestNews() {
        try {
            NewsResponse response = newsService.getLatestNews();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<NewsResponse> searchNews(@RequestParam String language, @RequestParam String category) {
        try {
            NewsResponse response = newsService.searchNews(language, category);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
