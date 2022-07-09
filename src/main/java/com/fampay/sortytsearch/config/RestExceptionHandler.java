package com.fampay.sortytsearch.config;

import com.fampay.sortytsearch.exception.ClientHttpResponseException;
import com.fampay.sortytsearch.exception.ForbiddenQuotaException;
import com.google.api.client.util.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;

public class RestExceptionHandler extends DefaultResponseErrorHandler {
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        HttpStatus httpStatus = clientHttpResponse.getStatusCode();
        HttpHeaders httpHeaders= clientHttpResponse.getHeaders();
        if(httpStatus.equals(HttpStatus.NOT_MODIFIED)){
            throw new ClientHttpResponseException(httpStatus,httpHeaders);
        } else if(httpStatus.equals(HttpStatus.FORBIDDEN)){
            InputStream inputStream = clientHttpResponse.getBody();
            String responseBody = IOUtils.deserialize(inputStream);
            throw new ForbiddenQuotaException(httpStatus,httpHeaders,responseBody);
        }
    }
}
