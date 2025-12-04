package com.example.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность для хранения расписаний проверки SSL-сертификатов.
 *
 * Соответствует таблице {@code check_schedule} в базе данных.
 * Хранит информацию о URL для проверки, cron-выражение для планирования,
 * статус активности и дату создания записи.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "check_schedule")
@Entity
public class CheckSchedule {

    /**
     * Уникальный идентификатор расписания.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * URL веб-ресурса, для которого будет выполняться проверка сертификата.
     */
    @Column(name = "url", nullable = false)
    private String url;

    /**
     * Cron-выражение для планирования периодических проверок.
     * Формат: "секунды минуты часы день_месяца месяц день_недели".
     */
    @Column(name = "cron_expression", nullable = false)
    private String cronExpression;

    /**
     * Флаг активности расписания.
     * Если {@code true}, задача будет выполняться согласно cron-выражению.
     */
    @Builder.Default
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    /**
     * Дата и время создания записи о расписании.
     */
    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}