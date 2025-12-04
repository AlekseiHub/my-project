package com.example.service.impl;

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

/**
 * Сервис для проверки SSL-сертификатов веб-ресурсов и сохранения результатов в базу данных.
 *
 * Реализует {@link CertificateCheckerService} и выполняет проверку сертификатов по URL,
 * полученным из {@link CheckSchedule}, а также сохраняет результаты в {@link CertificateResultRepository}.
 *
 * Основные функции:
 *Подключение к URL по HTTPS и получение сертификата сервера
 *Определение валидности сертификата на текущий момент
 *Формирование DTO {@link CertificateCheckResult} и сохранение его в базу данных
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CertificateCheckerServiceImpl implements CertificateCheckerService {

    private final CertificateResultRepository certificateResultRepository;
    public final CertificateResultMapper certificateResultMapper;

    /**
     * Проверяет сертификат по расписанию и сохраняет результат в базу данных.
     *
     * @param schedule объект {@link CheckSchedule}, содержащий URL и параметры проверки
     * @throws RuntimeException если произошла ошибка при проверке сертификата
     */
    @Override
    public void checkAndSave(CheckSchedule schedule) {

        try {
            CertificateCheckResult response = check(schedule);

            certificateResultRepository.save(response);

            log.info("Сертификат по URL '{}' проверен и сохранён", schedule.getUrl());

        } catch (RuntimeException e) {
            throw new RuntimeException("Ошибка проверки сертификата");
        }
    }

    /**
     * Выполняет проверку SSL-сертификата по URL расписания.
     *
     * Подключается к серверу, извлекает X509-сертификат и определяет его:
     * subject и issuer
     * даты начала и окончания действия
     * валидность на текущий момент
     * формирует JSON-представление сертификата
     *
     * @param checkSchedule объект {@link CheckSchedule} с URL для проверки
     * @return {@link CertificateCheckResult} с результатами проверки
     * @throws RuntimeException если не удалось подключиться к серверу или получить сертификат
     */
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

    /**
     * Конвертирует {@link Date} в {@link LocalDateTime} с использованием системного часового пояса.
     *
     * @param date объект {@link Date} для конвертации
     * @return объект {@link LocalDateTime}
     */
    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}