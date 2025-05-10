package ru.yandex.practicum.ewm;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Set;

@Component
public class RestStatClient implements StatClient {
    private final RestClient restClient;

    public RestStatClient() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:9090")
                .build();
    }

    @Override
    public void hit(EndpointHitCreateDto endpointHitDto) {

        EndpointHitDto savedHit = restClient.post()
                .uri("/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .body(endpointHitDto)
                .retrieve()
                .body(EndpointHitDto.class);

        System.out.println(savedHit.toString());
    }

    @Override
    public List<ViewStatsDto> getStats(String start, String end, Set<String> uris, Boolean unique) {
        List<ViewStatsDto> viewStats = restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/stats")
                .queryParam("start", start)
                .queryParam("end", end)
                .queryParam("uris", uris)
                .queryParam("unique", unique)
                .build())
            .header("Content-Type", "application/json")
            .retrieve()
            .body(new ParameterizedTypeReference<List<ViewStatsDto>>() {});

        System.out.println(viewStats.toString());

        return viewStats;
    }
}
