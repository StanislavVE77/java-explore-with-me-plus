package ru.yandex.practicum.ewm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHitDto createHit(@RequestBody @Valid EndpointHitCreateDto endpointHitDto) {
        log.info("Пришел POST запрос /hit с телом {}", endpointHitDto);
        EndpointHitDto newEndpointHit = statService.createStat(endpointHitDto);
        log.info("Метод POST /hit вернул ответ {}", newEndpointHit);
        return newEndpointHit;
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStatsDto> getStats(@RequestParam(value = "start") String start,
                                       @RequestParam(value = "end") String end,
                                       @RequestParam(required = false) Set<String> uris,
                                       @RequestParam(required = false, defaultValue = "false") Boolean unique) {
        log.info("Пришел GET запрос /stats  с параметрами start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDt = dateTimeFormat.parse(start, LocalDateTime::from);
        LocalDateTime endDt = dateTimeFormat.parse(end, LocalDateTime::from);
        List<ViewStatsDto> stats = statService.getStats(startDt, endDt, uris, unique);
        log.info("Метод GET /stats вернул ответ {}", stats);
        return stats;
    }

}
