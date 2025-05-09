package ru.yandex.practicum.ewm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EndpointHit {

    @Null
    private Integer id;

    @NotNull
    private String app;

    @NotNull
    private String uri;

    @NotNull
    private String ip;

    @NotNull
    private String timestamp;
}
