package com.drgtn.aiomed.parser;

import com.drgtn.aiomed.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.ZoneId.systemDefault;
import static java.time.ZonedDateTime.ofInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecurrencePatternExecutorIT extends BaseIT {
    @Autowired
    private RecurrencePatternExecutor recurrencePatternExecutor;

    @Test
    public void testComputeExecutionsDailyPattern_twoTimesPerDay() {
        ZonedDateTime start = ofInstant(Instant.parse("2025-04-30T08:00:00Z"), systemDefault());
        ZonedDateTime end = ofInstant(Instant.parse("2025-04-30T08:00:00Z").plus(Duration.ofDays(2)), systemDefault());
        assertThat(recurrencePatternExecutor.computeExecutions("every day at 08:00 and 18:00", start, end))
                .containsExactlyInAnyOrder(
                        Instant.parse("2025-04-30T15:00:00Z").atZone(ZoneId.systemDefault()),
                        Instant.parse("2025-05-01T05:00:00Z").atZone(systemDefault()),
                        Instant.parse("2025-05-01T15:00:00Z").atZone(systemDefault()),
                        Instant.parse("2025-05-02T05:00:00Z").atZone(systemDefault()));
    }

    @Test
    public void testComputeExecutionsDailyPattern_oneTimePerDay() {
        ZonedDateTime start = ofInstant(Instant.parse("2025-04-30T08:00:00Z"), systemDefault());
        ZonedDateTime end = ofInstant(Instant.parse("2025-04-30T08:00:00Z").plus(Duration.ofDays(2)), systemDefault());
        assertThat(recurrencePatternExecutor.computeExecutions("every day at 08:00", start, end))
                .containsExactlyInAnyOrder(
                        Instant.parse("2025-05-01T05:00:00Z").atZone(ZoneId.systemDefault()),
                        Instant.parse("2025-05-02T05:00:00Z").atZone(systemDefault()));
    }

    @Test
    public void testComputeExecutions_weeklyPattern() {
        ZonedDateTime start = ofInstant(Instant.parse("2025-04-30T08:00:00Z"), systemDefault());
        ZonedDateTime end = ofInstant(Instant.parse("2025-04-30T08:00:00Z").plus(Duration.ofDays(30)), systemDefault());
        assertThat(recurrencePatternExecutor.computeExecutions("every Monday at 10:00", start, end))
                .containsExactlyInAnyOrder(
                        Instant.parse("2025-05-05T07:00:00Z").atZone(ZoneId.systemDefault()),
                        Instant.parse("2025-05-12T07:00:00Z").atZone(ZoneId.systemDefault()),
                        Instant.parse("2025-05-19T07:00:00Z").atZone(ZoneId.systemDefault()),
                        Instant.parse("2025-05-26T07:00:00Z").atZone(ZoneId.systemDefault())
                );
    }

    @Test
    public void testComputeExecutions_BadPattern() {
        ZonedDateTime start = ofInstant(Instant.parse("2025-04-30T08:00:00Z"), systemDefault());
        ZonedDateTime end = ofInstant(Instant.parse("2025-04-30T08:00:00Z").plus(Duration.ofDays(30)), systemDefault());
        assertThrows(RecurrencePatternException.class,
                () -> recurrencePatternExecutor.computeExecutions("every Decade at 10:00", start, end));
    }
}
