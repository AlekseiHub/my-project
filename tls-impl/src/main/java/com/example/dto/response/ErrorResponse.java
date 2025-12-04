package com.example.dto.response;

/**
 * Универсальный DTO-ответ для передачи информации об ошибках клиенту.
 *
 * Содержит статус ошибки, её код и подробное описание.
 * Используется глобальными обработчиками исключений или сервисами,
 * когда необходимо вернуть структурированный ответ об ошибке.
 */
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