package ru.yandex.practicum.ewm;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.yandex.practicum.ewm.dto.EndpointHit;
import ru.yandex.practicum.ewm.dto.ViewStats;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class StatClient {
    private final RestTemplate restTemplate;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/stats")
                .queryParam("start", FORMATTER.format(start))
                .queryParam("end", FORMATTER.format(end))
                .queryParam("unique", unique);

        if (uris != null && !uris.isEmpty()) {
            for (String uri : uris) {
                uriBuilder.queryParam("uris", uri);
            }
        }

        URI uri = uriBuilder.build(true).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List<ViewStats>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        return response.getBody();
    }

    public ResponseEntity<Object> addHit(EndpointHit hit) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<EndpointHit> requestEntity = new HttpEntity<>(hit, headers);

        return restTemplate.exchange("/hit", HttpMethod.POST, requestEntity, Object.class);
    }
}