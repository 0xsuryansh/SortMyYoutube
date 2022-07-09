package com.fampay.sortytsearch.client.impl;

import com.fampay.sortytsearch.client.BaseHttpClient;
import com.fampay.sortytsearch.client.YoutubeClient;
import com.fampay.sortytsearch.client.response.YoutubeSearchListResponse;
import com.fampay.sortytsearch.config.YoutubeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.fampay.sortytsearch.constant.Constants.YoutubeRequestConstants.*;

@Component
public class YoutubeClientImpl extends BaseHttpClient implements YoutubeClient {

    private YoutubeConfig youtubeConfig;

    @Autowired
    public YoutubeClientImpl(RestTemplate _restTemplate,
                             YoutubeConfig _config){
        super(_restTemplate);
        youtubeConfig = _config;
    }

    @Override
    public YoutubeSearchListResponse fetchYoutubeSearchResults(String keywords) {
        String accessKey = youtubeConfig.getAccessKeyQueue().peek();
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        URI uri = UriComponentsBuilder.fromUriString(youtubeConfig.getBaseUrl()).
                    path(youtubeConfig.getSearchListEndpoint()).
                    queryParam(PART,SNIPPET).
                    queryParam(Q,keywords).
                    queryParam(TYPE,VIDEO).
                    queryParam(KEY,accessKey).
                    queryParam(PUB_AFTER,getIsoFormatDate(currentDateTime)).
                    queryParam(ORDER,DATE).encode().build().toUri();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept",MediaType.APPLICATION_JSON_VALUE);
            RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);
            ResponseEntity<YoutubeSearchListResponse> response = singleRequest(requestEntity, YoutubeSearchListResponse.class);
            return response.getBody();
    }

    private String getIsoFormatDate(LocalDateTime time){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ISO_DATE_FORMAT);
            return dateTimeFormatter.format(time);
    }
}
