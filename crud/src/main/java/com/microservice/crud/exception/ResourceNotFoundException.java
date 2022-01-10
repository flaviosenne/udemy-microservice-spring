package com.microservice.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6897059851520532219L;

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
