package com.product.api.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    // Getter y Setter
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
