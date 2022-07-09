package com.fampay.sortytsearch.controller;

import com.fampay.sortytsearch.client.YoutubeClient;
import com.fampay.sortytsearch.client.response.YoutubeSearchListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    private YoutubeClient client;

    @Autowired
    public SearchController(YoutubeClient _client){
        client = _client;
    }

    @GetMapping("/video")
    public YoutubeSearchListResponse getVideo(@RequestParam String word){
        return client.fetchYoutubeSearchResults(word);
    }
}
