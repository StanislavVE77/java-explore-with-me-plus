package ru.yandex.practicum.ewm;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface StatService {
    EndpointHitDto createStat(EndpointHitCreateDto userDto);

    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique);
}
