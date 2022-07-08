package com.fampay.sortytsearch.client.impl;

import com.fampay.sortytsearch.client.BaseHttpClient;
import com.fampay.sortytsearch.client.YoutubeClient;
import com.fampay.sortytsearch.config.YoutubeConfig;
import com.google.api.services.youtube.model.SearchListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import static com.fampay.sortytsearch.constant.Constants.YoutubeRequestConstants.*;

import java.net.URI;
import java.util.Date;

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
    public SearchListResponse fetchYoutubeSearchResults(String keywords) {
            URI uri = UriComponentsBuilder.fromUriString(youtubeConfig.getBaseUrl()).
                    path(youtubeConfig.getSearchListEndpoint()).
                    queryParam(PART,SNIPPET).
                    queryParam(Q,keywords).
                    queryParam(TYPE,VIDEO).
                    queryParam(PUB_BEFORE, new Date()).
                    queryParam(ORDER,DATE).encode().build().toUri();
            HttpHeaders headers = new HttpHeaders();
            String accessKey = youtubeConfig.getAccessKeyQueue().peek();
            headers.set(KEY,accessKey);
            RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);
            ResponseEntity<SearchListResponse> response = singleRequest(requestEntity, SearchListResponse.class);

            return response.getBody();
    }
}
