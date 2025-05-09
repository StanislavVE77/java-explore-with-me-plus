package ru.yandex.practicum.ewm.client;

import ru.yandex.practicum.ewm.StatClient;
import ru.yandex.practicum.ewm.dto.EndpointHit;
import ru.yandex.practicum.ewm.dto.ViewStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.client.RestTemplate;

@Service
public class Client {
    private final StatClient statClient;

    @Autowired
    public Client(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build();

        this.statClient = new StatClient(restTemplate);
    }

    public ResponseEntity<Object> addHit(EndpointHit hit) {
        return statClient.addHit(hit);
    }

    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return statClient.getStats(start, end, uris, unique);
    }
}