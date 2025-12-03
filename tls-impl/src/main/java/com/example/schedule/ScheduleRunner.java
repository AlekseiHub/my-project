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

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleRunner {

    private final CheckScheduleRepository checkScheduleRepository;

    private final CertificateCheckerService certificateCheckerService;

    private final TaskScheduler taskScheduler;

    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        log.info("Инициализация планировщика…");
        startAllActiveSchedules();
    }

    public void startAllActiveSchedules() {
        List<CheckSchedule> schedules = checkScheduleRepository.findAllByActiveTrue();

        for (CheckSchedule schedule : schedules) {
            startSchedule(schedule);
        }
    }

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