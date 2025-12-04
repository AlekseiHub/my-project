package com.example.service;

import com.example.model.entity.CertificateCheckResult;
import com.example.model.entity.CheckSchedule;

/**
 * Сервис для проверки SSL-сертификатов веб-ресурсов.
 *
 * Предоставляет методы для выполнения проверки сертификата по расписанию и сохранения результатов в базу данных.
 * Используется в {@link com.example.schedule.ScheduleRunner} для автоматического запуска задач.
 */
public interface CertificateCheckerService {

    /**
     * Проверяет сертификат по заданному расписанию и сохраняет результат в базу данных.
     *
     * @param schedule объект {@link CheckSchedule}, содержащий URL и параметры проверки
     * @throws RuntimeException если произошла ошибка при проверке сертификата
     */
    void checkAndSave(CheckSchedule schedule);

    /**
     * Выполняет проверку SSL-сертификата по URL из {@link CheckSchedule} без автоматического сохранения.
     *
     * @param checkSchedule объект {@link CheckSchedule} с URL для проверки
     * @return объект {@link CertificateCheckResult} с результатами проверки
     * @throws RuntimeException если не удалось подключиться к серверу или получить сертификат
     */
    CertificateCheckResult check(CheckSchedule checkSchedule);
}
