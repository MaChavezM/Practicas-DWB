package com.product.api.controller;

/**
 * ApiResponse
 */
public class ApiResponse {
    private int status;
    private String message;

    public ApiResponse() {
    }

    public ApiResponse(int status, String message) {
        this.status = 200; // por defecto, si no se pasa un status, se asume que es 200
        this.message = message;
    }

    // Getters y Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
