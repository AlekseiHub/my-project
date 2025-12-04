package com.example.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO-ответ, содержащий результат проверки SSL-сертификата.
 *
 * Используется для передачи клиенту информации о последней или всех проверках,
 * включая данные сертификата, временные рамки его валидности и технические параметры.
 */
@Builder
public record CertificateResultResponse(
        LocalDateTime checkTime,
        String subject,
        String issuer,
        LocalDateTime notBefore,
        LocalDateTime notAfter,
        Boolean valid,
        String rawCertificateJson
) {
}