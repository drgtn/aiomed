package com.drgtn.aiomed.parser;

import java.time.ZonedDateTime;
import java.util.List;

public interface RecurrencePatternParser {
    List<ZonedDateTime> getNextOccurrences(String recurrencePattern, ZonedDateTime from, ZonedDateTime to);

    boolean matches(String pattern);
}
