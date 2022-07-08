package com.fampay.sortytsearch.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

@Data
@Configuration
@ConfigurationProperties(prefix="yt")
public class YoutubeConfig {
    private String baseUrl;
    private String searchListEndpoint;
    private String[] accessKeyList;
    private Queue<String> accessKeyQueue;

    @PostConstruct
    private void makeQueue(){
        accessKeyQueue = new LinkedList<>(Arrays.asList(accessKeyList));
    }
}
