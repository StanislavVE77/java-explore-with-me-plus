package ru.yandex.practicum.ewm.mapper;

import ru.yandex.practicum.ewm.dto.EndpointHit;
import ru.yandex.practicum.ewm.dto.ViewStats;
import ru.yandex.practicum.ewm.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class StatsMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static List<ViewStats> toListViewNotUnique(List<Hit> hits, Boolean unique) {
        if (hits == null || hits.isEmpty()) return Collections.emptyList();

        Map<String, Integer> uriStats = countUriHits(hits);
        List<ViewStats> viewStats = unique != null && unique
                ? buildUniqueStats(hits, uriStats.keySet())
                : buildNonUniqueStats(uriStats);

        return viewStats.stream()
                .sorted(Comparator.comparingInt(ViewStats::getHits).reversed())
                .collect(Collectors.toList());
    }

    public static Hit toHit(EndpointHit hit) {
        return new Hit(
                null,
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                LocalDateTime.parse(hit.getTimestamp(), FORMATTER)
        );
    }

    private static Map<String, Integer> countUriHits(List<Hit> hits) {
        Map<String, Integer> uriStats = new HashMap<>();
        for (Hit hit : hits) {
            uriStats.merge(hit.getUri(), 1, Integer::sum);
        }
        return uriStats;
    }

    private static List<ViewStats> buildNonUniqueStats(Map<String, Integer> uriStats) {
        List<ViewStats> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : uriStats.entrySet()) {
            list.add(new ViewStats("ewm-main-service", entry.getKey(), entry.getValue()));
        }
        return list;
    }

    private static List<ViewStats> buildUniqueStats(List<Hit> hits, Set<String> uris) {
        Map<String, Set<String>> uriToIps = new HashMap<>();
        for (Hit hit : hits) {
            uriToIps.computeIfAbsent(hit.getUri(), k -> new HashSet<>()).add(hit.getIp());
        }

        List<ViewStats> list = new ArrayList<>();
        for (String uri : uris) {
            int uniqueIpCount = uriToIps.getOrDefault(uri, Collections.emptySet()).size();
            list.add(new ViewStats("ewm-main-service", uri, uniqueIpCount));
        }
        return list;
    }
}
