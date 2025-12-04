package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;


/**
 * Конфигурация планировщика задач Spring.
 *
 * Определяет {@link TaskScheduler}, используемый для выполнения задач,
 * запланированных при помощи аннотаций {@Scheduled}, триггеров или Cron-выражений.
 *
 * В качестве реализации используется {@link ThreadPoolTaskScheduler} с пулом из 5 потоков,
 * что позволяет выполнять несколько задач одновременно.
 */
@Configuration
public class SchedulerConfig {

    /**
     *Создаёт и настраивает пул потоков для выполнения планируемых задач.
     *poolSize = 5 — максимальное число одновременно работающих потоков.
     *threadNamePrefix = "CronScheduler-"</b> — удобный префикс для логирования.
     *
     * @return настроенный {@link TaskScheduler}, готовый к использованию в планировщике задач Spring.
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("CronScheduler-");
        scheduler.initialize();
        return scheduler;
    }
}