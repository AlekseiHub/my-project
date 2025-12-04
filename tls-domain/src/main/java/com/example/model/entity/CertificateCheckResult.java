package com.example.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность для хранения результатов проверки SSL-сертификатов.
 *
 * Соответствует таблице {@code certificate_check_result} в базе данных.
 * Хранит информацию о проверенном сертификате, времени проверки,
 * валидности и исходные данные в формате JSON.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "certificate_check_result")
@Entity
public class CertificateCheckResult {

    /**
     * Уникальный идентификатор записи.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор расписания, к которому относится эта проверка.
     * Ссылается на {@code CheckSchedule}.
     */
    @JoinColumn(name = "schedule_id", nullable = false)
    private Long scheduleId;

    /**
     * Время выполнения проверки сертификата.
     */
    @Column(name = "check_time", nullable = false)
    private LocalDateTime checkTime;

    /**
     * Subject сертификата (информация о владельце).
     */
    @Column(name = "subject", nullable = false)
    private String subject;

    /**
     * Issuer сертификата (выдавший сертификат центр сертификации).
     */
    @Column(name = "issuer", nullable = false)
    private String issuer;

    /**
     * Дата начала действия сертификата.
     */
    @Column(name = "not_before", nullable = false)
    private LocalDateTime notBefore;

    /**
     * Дата окончания действия сертификата.
     */
    @Column(name = "not_after", nullable = false)
    private LocalDateTime notAfter;

    /**
     * Флаг, показывающий, действителен ли сертификат на момент проверки.
     */
    @Column(name = "valid", nullable = false)
    private Boolean valid;

    /**
     * Сырые данные сертификата в формате JSON.
     * Используется для хранения полной информации о сертификате.
     */
    @Column(name = "raw_json", columnDefinition = "TEXT")
    private String rawJson;
}