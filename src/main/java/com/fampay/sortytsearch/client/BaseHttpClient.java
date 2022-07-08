package com.fampay.sortytsearch.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class BaseHttpClient {
    private final RestTemplate restTemplate;
    protected <T> ResponseEntity<T> singleRequest(RequestEntity<?> requestEntity , Class clazz){
        try{
            ResponseEntity<T> responseEntity = restTemplate.exchange(requestEntity, clazz);
            return responseEntity;
        } catch (Exception e){
            throw e;
        }
    }
}
