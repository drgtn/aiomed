package com.drgtn.aiomed.parser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class RecurrencePatternExecutor {
    private final List<RecurrencePatternParser> parsers;

    public List<ZonedDateTime> computeExecutions(String pattern, ZonedDateTime from, ZonedDateTime to) {
        return parsers.stream()
                .filter(patternParser -> patternParser.matches(pattern))
                .findFirst()
                .map(patternParser -> patternParser.getNextOccurrences(pattern, from, to))
                .orElseThrow(() -> new RecurrencePatternException("Unsupported pattern: " + pattern));
    }
}
