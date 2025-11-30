package com.example.dto.response;

import java.time.LocalDateTime;

public record CertificateResultDto(

        LocalDateTime checkTime,
        String subject,
        String issuer,
        LocalDateTime notBefore,
        LocalDateTime notAfter,
        Boolean valid,
        String rawCertificateJson
) {
}
