package com.farmershelper.farmerguidenceapplication.clients;

import com.farmershelper.farmerguidenceapplication.models.NewsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "newsClient", url = "${newsapi.url}")
public interface NewsClient {
    @GetMapping("/latest-news")
    NewsResponse getLatestNews(@RequestParam("apiKey") String apiKey);

    @GetMapping("/search")
    NewsResponse searchNews(@RequestParam("apiKey") String apiKey,
                            @RequestParam("language") String language,
                            @RequestParam("category") String category);
}
