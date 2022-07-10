package com.fampay.sortytsearch.controller;

import com.fampay.sortytsearch.client.YoutubeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @Autowired
    private YoutubeClient youtubeClient;

    /**
     * Health check
     * @return if application is up succesfully
     */
    @GetMapping("health")
    public String healthCheck(){
        return "Working!";
    }
}
