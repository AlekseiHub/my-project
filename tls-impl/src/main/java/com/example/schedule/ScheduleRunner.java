package com.example.schedule;

import com.example.model.entity.CheckSchedule;
import com.example.model.repository.CheckScheduleRepository;
import com.example.service.CertificateCheckerService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * Компонент для управления запуском и управлением задач проверки сертификатов по расписанию.
 *
 * Использует {@link TaskScheduler} для планирования выполнения задач на основе cron-выражений.

 * Основные функции:
 *Инициализация всех активных расписаний после старта приложения
 *Запуск новых задач на основе cron-выражений
 *Хранение ссылок на {@link ScheduledFuture} для управления запущенными задачами
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleRunner {

    private final CheckScheduleRepository checkScheduleRepository;

    private final CertificateCheckerService certificateCheckerService;

    private final TaskScheduler taskScheduler;

    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    /**
     * Метод инициализации, вызывается после создания бина.
     *
     * Загружает все активные расписания из базы данных и запускает их.
     */
    @PostConstruct
    public void init() {
        log.info("Инициализация планировщика…");
        startAllActiveSchedules();
    }

    /**
     * Запускает все активные расписания, хранящиеся в базе данных.
     */
    public void startAllActiveSchedules() {
        List<CheckSchedule> schedules = checkScheduleRepository.findAllByActiveTrue();

        for (CheckSchedule schedule : schedules) {
            startSchedule(schedule);
        }
    }

    /**
     * Запускает конкретное расписание.
     *
     * Создаёт задачу с cron-выражением {@link CheckSchedule#getCronExpression()}.
     * Выполнение задачи делегируется {@link CertificateCheckerService#checkAndSave(CheckSchedule)}.
     * Ошибки при запуске логируются.
     *
     * @param schedule сущность расписания для запуска
     */
    public void startSchedule(CheckSchedule schedule) {

        String cron = schedule.getCronExpression();

        try {
            log.info("Старт задачи id={}, cron={}", schedule.getId(), cron);

            ScheduledFuture<?> future = taskScheduler.schedule(
                    () -> certificateCheckerService.checkAndSave(schedule),
                    new CronTrigger(cron)
            );

            scheduledTasks.put(schedule.getId(), future);

        } catch (Exception e) {
            log.error("Ошибка при запуске cron задачи id={} cron={}", schedule.getId(), cron, e);
        }
    }
}