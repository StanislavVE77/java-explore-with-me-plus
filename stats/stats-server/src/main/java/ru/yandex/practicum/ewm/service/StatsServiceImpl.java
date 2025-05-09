package ru.yandex.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewm.model.Hit;
import ru.yandex.practicum.ewm.storage.StatsStorage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsStorage statsStorage;

    @Override
    public void addHit(Hit hit) {
        statsStorage.save(hit);
    }

    @Override
    public List<Hit> getStats(String start, String end, List<String> uris) {
        validateDateRange(start, end);
        return statsStorage.getStats(start, end, uris);
    }

    private void validateDateRange(String start, String end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("The request must contain both 'start' and 'end' parameters.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(start, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);

        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("'start' must not be after 'end'.");
        }
    }
}
