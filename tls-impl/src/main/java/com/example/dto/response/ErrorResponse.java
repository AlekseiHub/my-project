package com.example.dto.response;

public record ErrorResponse(
        String status,
        int code,
        String details
) {
    public static ErrorResponse from(ErrorType errorType) {
        return new ErrorResponse(
                errorType.getStatus(),
                errorType.getCode(),
                errorType.getDetail());
    }
}