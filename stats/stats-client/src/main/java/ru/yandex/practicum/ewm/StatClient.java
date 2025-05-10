package ru.yandex.practicum.ewm;

import java.util.List;
import java.util.Set;

public interface StatClient {
    void hit(EndpointHitCreateDto paramHitDto);

    List<ViewStatsDto> getStats(String start, String end, Set<String> uris, Boolean unique);
}
