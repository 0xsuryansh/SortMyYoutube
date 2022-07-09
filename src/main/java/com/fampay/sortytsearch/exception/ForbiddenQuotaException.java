package com.fampay.sortytsearch.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Getter
public class ForbiddenQuotaException extends RuntimeException{
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
    private String responseBody;
}
