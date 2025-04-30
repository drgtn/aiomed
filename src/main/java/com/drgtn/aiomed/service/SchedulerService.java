package com.drgtn.aiomed.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerService {
    public void schedule(Runnable task, LocalDateTime startTime, Duration recurrence) {
        long initialDelayMillis = Duration.between(LocalDateTime.now(), startTime).toMillis();

        initialDelayMillis = Math.max(0, initialDelayMillis);
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(
                task,
                initialDelayMillis,
                recurrence.toMillis(),
                TimeUnit.MILLISECONDS
        );

        log.info("Task scheduled to start at: {}, every {} minutes", startTime, recurrence.toMinutes());
    }

}
