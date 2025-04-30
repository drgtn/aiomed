package com.drgtn.aiomed.parser;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static java.time.ZoneId.systemDefault;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DailyPatternParserTest {
    private final DailyPatternParser dailyPatternParser = new DailyPatternParser();

    @Test
    void testMatches_validPattern() {
        assertTrue(dailyPatternParser.matches("every day at 08:00"));
    }

    @Test
    void testMatches_validPatternMultipleHours() {
        assertTrue(dailyPatternParser.matches("every day at 08:00 and 12:00 and 16:00"));
    }

    @Test
    void testMatches_invalidPattern() {
        String invalidPattern = "every week at 08:00";
        assertFalse(dailyPatternParser.matches(invalidPattern));
    }

    @Test
    void testGetNextOccurrences_singleOccurrence() {
        ZonedDateTime now = ZonedDateTime.of(2025, 4, 29, 7, 30, 0, 0, systemDefault());
        ZonedDateTime to = now.plusDays(1);
        assertThat(dailyPatternParser.getNextOccurrences("every day at 08:00", now, to))
                .containsExactly(
                        ZonedDateTime.of(2025, 4, 29, 8, 0, 0, 0, systemDefault()));
    }

    @Test
    void testGetNextOccurrences_multipleOccurrences() {
        ZonedDateTime now = ZonedDateTime.of(2025, 4, 29, 7, 30, 0, 0, systemDefault());
        ZonedDateTime to = now.plusDays(1);

        assertThat(dailyPatternParser.getNextOccurrences("every day at 08:00 and 12:00", now, to))
                .containsExactlyInAnyOrder(
                        ZonedDateTime.of(2025, 4, 29, 8, 0, 0, 0, systemDefault()),
                        ZonedDateTime.of(2025, 4, 29, 12, 0, 0, 0, systemDefault())
                );
    }


    @Test
    void testGetNextOccurrences_noOccurrencesInRange() {
        ZonedDateTime now = ZonedDateTime.of(2025, 4, 29, 10, 30, 0, 0, systemDefault());
        ZonedDateTime to = now.plusHours(1);

        assertThat(dailyPatternParser.getNextOccurrences("every day at 08:00", now, to)).isEmpty();
    }
}
