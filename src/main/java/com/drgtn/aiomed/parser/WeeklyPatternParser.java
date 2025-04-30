package com.drgtn.aiomed.parser;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class WeeklyPatternParser implements RecurrencePatternParser {
    private static final Pattern PATTERN = Pattern.compile("every (Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday) at ([0-9]{2}:[0-9]{2})");

    @Override
    public boolean matches(String pattern) {
        return PATTERN.matcher(pattern).matches();
    }

    @Override
    public List<ZonedDateTime> getNextOccurrences(String recurrencePattern,
                                                  ZonedDateTime from,
                                                  ZonedDateTime to) {
        Matcher matcher = PATTERN.matcher(recurrencePattern);
        if (!matcher.find()) return Collections.emptyList();

        return getMatchingOccurrences(
                from,
                to,
                parseDayOfWeek(matcher.group(1)),
                LocalTime.parse(matcher.group(2)));
    }

    private DayOfWeek parseDayOfWeek(String day) {
        return DayOfWeek.valueOf(day.toUpperCase());
    }

    private List<ZonedDateTime> getMatchingOccurrences(ZonedDateTime from,
                                                       ZonedDateTime to,
                                                       DayOfWeek targetDay,
                                                       LocalTime targetTime) {
        return from.toLocalDate()
                .datesUntil(to.toLocalDate().plusDays(1))
                .filter(date -> date.getDayOfWeek().equals(targetDay))
                .map(date -> date.atTime(targetTime).atZone(from.getZone()))
                .filter(dt -> !dt.isBefore(from) && !dt.isAfter(to))
                .toList();
    }
}
