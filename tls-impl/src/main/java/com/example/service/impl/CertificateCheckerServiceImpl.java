package com.example.service.impl;

import com.example.dto.response.CertificateResultResponse;
import com.example.mapper.CertificateResultMapper;
import com.example.model.entity.CertificateCheckResult;
import com.example.model.entity.CheckSchedule;
import com.example.model.repository.CertificateResultRepository;
import com.example.service.CertificateCheckerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class CertificateCheckerServiceImpl implements CertificateCheckerService {

    private final CertificateResultRepository certificateResultRepository;
    public final CertificateResultMapper certificateResultMapper;

    @Override
    public void checkAndSave(CheckSchedule schedule) {

        try {
            // 1. Проверить сертификат с URL
            CertificateCheckResult response = check(schedule);

            // 2. Сохранить
            certificateResultRepository.save(response);



            log.info("Сертификат по URL '{}' проверен и сохранён", schedule.getUrl());

        } catch (Exception e) {
            log.error("Ошибка проверки сертификата {}: {}", schedule.getUrl(), e.getMessage());
        }
    }

    @Override
    public CertificateCheckResult check(CheckSchedule checkSchedule) {
        try {
            URL urlresult = new URL(checkSchedule.getUrl());
            HttpsURLConnection connection = (HttpsURLConnection) urlresult.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            Certificate[] certificates = connection.getServerCertificates();
            if (certificates.length == 0) {
                throw new RuntimeException("Сертификат сервера не найден");
            }

            X509Certificate cert = (X509Certificate) certificates[0];
            LocalDateTime notBefore = toLocalDateTime(cert.getNotBefore());
            LocalDateTime notAfter = toLocalDateTime(cert.getNotAfter());
            LocalDateTime now = LocalDateTime.now();
            String subject = cert.getSubjectX500Principal().getName();
            String issuer = cert.getIssuerX500Principal().getName();

            boolean validNow = now.isAfter(notBefore) && now.isBefore(notAfter);

            String rawJson = """
                {
                  "subject": "%s",
                  "issuer": "%s",
                  "notBefore": "%s",
                  "notAfter": "%s"
                }
                """.formatted(
                    cert.getSubjectDN().getName(),
                    cert.getIssuerDN().getName(),
                    notBefore,
                    notAfter
            );

            return CertificateCheckResult.builder()
                    .scheduleId(checkSchedule.getId())
                    .checkTime(LocalDateTime.now())
                    .subject(subject)
                    .issuer(issuer)
                    .notBefore(notBefore)
                    .notAfter(notAfter)
                    .valid(validNow)
                    .rawJson(rawJson)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Ошибка проверки сертификата: " + e.getMessage(), e);
        }
    }
    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}