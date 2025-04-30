package com.drgtn.aiomed.parser;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;

@Component
class DailyPatternParser implements RecurrencePatternParser {
    private static final Pattern PATTERN = Pattern.compile("every day at ((?:[0-9]{2}:[0-9]{2})(?: and [0-9]{2}:[0-9]{2})*)");

    private static List<LocalTime> parseDailyTimes(Matcher matcher) {
        return Arrays.stream(matcher.group(1).split(" and "))
                .map(String::trim)
                .map(LocalTime::parse)
                .toList();
    }

    private static List<ZonedDateTime> generateOccurrencesBetween(ZonedDateTime from,
                                                                  ZonedDateTime to,
                                                                  List<LocalTime> times) {
        return from.toLocalDate().datesUntil(to.toLocalDate().plusDays(1))
                .flatMap(date -> times.stream()
                        .map(time -> date.atTime(time).atZone(from.getZone()))
                        .filter(occurrence -> !occurrence.isBefore(from) && !occurrence.isAfter(to)))
                .toList();
    }

    @Override
    public boolean matches(String pattern) {
        return PATTERN.matcher(pattern).matches();
    }

    @Override
    public List<ZonedDateTime> getNextOccurrences(String recurrencePattern, ZonedDateTime from, ZonedDateTime to) {
        Matcher matcher = PATTERN.matcher(recurrencePattern);
        if (!matcher.find()) {
            return emptyList();
        }
        return generateOccurrencesBetween(from, to, parseDailyTimes(matcher));
    }


}
