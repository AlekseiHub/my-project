package com.example.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

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