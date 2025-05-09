package ru.yandex.practicum.ewm.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.ewm.dto.EndpointHit;
import ru.yandex.practicum.ewm.dto.ViewStats;
import ru.yandex.practicum.ewm.mapper.StatsMapper;
import ru.yandex.practicum.ewm.service.StatsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statService;

    @PostMapping(path = "/hit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addHit(@RequestBody @Valid EndpointHit hit) {
        statService.addHit(StatsMapper.toHit(hit));
    }

    @GetMapping(path = "/stats")
    public List<ViewStats> getStats(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam(required = false) Boolean unique,
                                    @RequestParam(required = false) List<String> uris) {
        return StatsMapper.toListViewNotUnique(statService.getStats(start, end, uris), unique);
    }
}
