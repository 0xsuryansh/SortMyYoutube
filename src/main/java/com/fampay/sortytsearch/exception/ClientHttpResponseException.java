package com.fampay.sortytsearch.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class ClientHttpResponseException extends RuntimeException{
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
}
