package com.drgtn.aiomed.parser;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static java.time.ZoneId.systemDefault;
import static org.assertj.core.api.Assertions.assertThat;

class WeeklyPatternParserTest {

    private final WeeklyPatternParser weeklyPatternParser = new WeeklyPatternParser();

    @Test
    void testMatches_validPattern() {
        assertThat(weeklyPatternParser.matches("every Monday at 08:00")).isTrue();
    }

    @Test
    void testMatches_invalidPattern() {
        assertThat(weeklyPatternParser.matches("every Wrong at 08:00")).isFalse();

    }

    @Test
    void testGetNextOccurrences_withinOneWeek() {
        ZonedDateTime from = ZonedDateTime.of(2025, 4, 28, 7, 0, 0, 0, systemDefault()); // Monday
        ZonedDateTime to = from.plusDays(6);
        assertThat(weeklyPatternParser.getNextOccurrences("every Monday at 08:00", from, to)).containsExactly(ZonedDateTime.of(2025, 4, 28, 8, 0, 0, 0, systemDefault()));
    }

    @Test
    void testGetNextOccurrences_multipleWeeks() {
        ZonedDateTime from = ZonedDateTime.of(2025, 4, 28, 7, 0, 0, 0, systemDefault()); // Monday
        ZonedDateTime to = from.plusWeeks(2);
        assertThat(weeklyPatternParser.getNextOccurrences("every Monday at 08:00", from, to)).containsExactly(ZonedDateTime.of(2025, 4, 28, 8, 0, 0, 0, systemDefault()), ZonedDateTime.of(2025, 5, 5, 8, 0, 0, 0, systemDefault()));
    }

    @Test
    void testGetNextOccurrences_noOccurrencesInRange() {
        ZonedDateTime from = ZonedDateTime.of(2024, 4, 30, 9, 0, 0, 0, systemDefault()); // Tuesday
        ZonedDateTime to = from.plusDays(5);

        assertThat(weeklyPatternParser.getNextOccurrences("every Monday at 08:00", from, to)).isEmpty();
    }
}
