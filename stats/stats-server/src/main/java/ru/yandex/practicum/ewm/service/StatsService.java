package ru.yandex.practicum.ewm.service;

import ru.yandex.practicum.ewm.model.Hit;
import java.util.List;

public interface StatsService {

    void addHit(Hit hit);

    List<Hit> getStats(String start, String end, List<String> uris);

}
