package com.example.schedule;

import com.example.model.entity.CheckSchedule;
import com.example.model.repository.CheckScheduleRepository;
import com.example.service.CertificateCheckerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleRunnerTest {

    @Mock
    private CheckScheduleRepository checkScheduleRepository;

    @Mock
    private CertificateCheckerService certificateCheckerService;

    @Mock
    private TaskScheduler taskScheduler;

    @Mock
    private ScheduledFuture<?> scheduledFuture;

    @InjectMocks
    private ScheduleRunner scheduleRunner;

    private CheckSchedule schedule1;
    private CheckSchedule schedule2;

    @BeforeEach
    void setUp() {
        schedule1 = mock(CheckSchedule.class);
        schedule2 = mock(CheckSchedule.class);

        lenient().when(schedule1.getId()).thenReturn(1L);
        lenient().when(schedule2.getId()).thenReturn(2L);
        lenient().when(schedule1.getCronExpression()).thenReturn("0 0 * * * *");
        lenient().when(schedule2.getCronExpression()).thenReturn("0 15 * * * *");
    }

    @Test
    void testInit_StartsAllActiveSchedules() {
        List<CheckSchedule> schedules = List.of(schedule1, schedule2);
        when(checkScheduleRepository.findAllByActiveTrue()).thenReturn(schedules);
        doReturn(scheduledFuture).when(taskScheduler)
                .schedule(any(Runnable.class), any(CronTrigger.class));

        scheduleRunner.init();

        verify(taskScheduler, times(2))
                .schedule(any(Runnable.class), any(CronTrigger.class));
    }

    @Test
    void testStartAllActiveSchedules_WithNoSchedules() {
        when(checkScheduleRepository.findAllByActiveTrue()).thenReturn(List.of());

        scheduleRunner.startAllActiveSchedules();

        verify(taskScheduler, never()).schedule(any(Runnable.class), any(CronTrigger.class));
    }

    @Test
    void testStartSchedule_SchedulesTask() {
        doReturn(scheduledFuture).when(taskScheduler)
                .schedule(any(Runnable.class), any(CronTrigger.class));

        scheduleRunner.startSchedule(schedule1);

        verify(taskScheduler, times(1))
                .schedule(any(Runnable.class), any(CronTrigger.class));
    }

    @Test
    void testStartSchedule_TaskAddedToMap() {
        doReturn(scheduledFuture).when(taskScheduler)
                .schedule(any(Runnable.class), any(CronTrigger.class));

        scheduleRunner.startSchedule(schedule1);

        verify(taskScheduler, times(1)).schedule(
                any(Runnable.class),
                any(CronTrigger.class)
        );
    }

    @Test
    void testStartSchedule_TaskSchedulerThrowsException() {
        when(taskScheduler.schedule(any(Runnable.class), any(CronTrigger.class)))
                .thenThrow(new RuntimeException("Scheduler error"));

        assertDoesNotThrow(() -> scheduleRunner.startSchedule(schedule1));
    }
}