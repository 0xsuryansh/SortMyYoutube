package com.fampay.sortytsearch.client.impl;

import com.fampay.sortytsearch.client.BaseHttpClient;
import com.fampay.sortytsearch.client.YoutubeClient;
import com.fampay.sortytsearch.client.response.YoutubeSearchListResponse;
import com.fampay.sortytsearch.config.YoutubeConfig;
import com.fampay.sortytsearch.exception.ClientHttpResponseException;
import com.fampay.sortytsearch.exception.ForbiddenQuotaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.fampay.sortytsearch.constant.Constants.YoutubeRequestConstants.*;

@Component
public class YoutubeClientImpl extends BaseHttpClient implements YoutubeClient {

    private final YoutubeConfig youtubeConfig;

    /** @DevNotice : E-tag is cached in the execution context
     * If the resource has changed, the downstream API returns the modified resource and the ETag associated with that version of the resource.
     * If the resource has not changed, the API returns an HTTP 304 response
     * which indicates that the resource has not changed.
     * This helps reduce latency and bandwidth usage by serving cached resources in this manner.
     **/
    private String E_TAG_CACHED;

    @Autowired
    public YoutubeClientImpl(RestTemplate _restTemplate,
                             YoutubeConfig _config) {
        super(_restTemplate);
        youtubeConfig = _config;
        E_TAG_CACHED = "default";
    }

    /**
     * @param keywords
     * @return reponse from youtube API
     */
    @Override
    @Retryable(maxAttempts = 1, value = {ForbiddenQuotaException.class})
    public YoutubeSearchListResponse fetchYoutubeSearchResults(String keywords) {
        String accessKey = youtubeConfig.getAccessKeyQueue().peek();
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        ResponseEntity<YoutubeSearchListResponse> response = null;
        try {
            URI uri = UriComponentsBuilder.fromUriString(youtubeConfig.getBaseUrl()).
                    path(youtubeConfig.getSearchListEndpoint()).
                    queryParam(PART, SNIPPET).
                    queryParam(Q, keywords).
                    queryParam(TYPE, VIDEO).
                    queryParam(KEY, accessKey).
                    queryParam(PUB_AFTER, getIsoFormatDate(currentDateTime)).
                    queryParam(ORDER, DATE).encode().build().toUri();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.setIfNoneMatch(E_TAG_CACHED);
            RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);
            response = singleRequest(requestEntity, YoutubeSearchListResponse.class);
            E_TAG_CACHED = response.getBody().getEtag();
        } catch (ClientHttpResponseException ex) {
            if (ex.getHttpStatus().equals(HttpStatus.NOT_MODIFIED)) {
                return null;
            }
        } catch (ForbiddenQuotaException ex) {
            /**Catching this exception means that API key's quota is exhausted
             * we use then next key in the queue*/
            youtubeConfig.getAccessKeyQueue().poll();
            /**The exception is thrown again for @Retryable*/
            throw ex;
        }
        return Objects.isNull(response)? response.getBody() : new YoutubeSearchListResponse();
    }

    private String getIsoFormatDate(LocalDateTime time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ISO_DATE_FORMAT);
        return dateTimeFormatter.format(time);
    }
}
