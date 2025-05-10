package ru.yandex.practicum.ewm.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.ewm.EndpointHitCreateDto;
import ru.yandex.practicum.ewm.EndpointHitDto;
import ru.yandex.practicum.ewm.ViewStatsDto;
import ru.yandex.practicum.ewm.model.Stat;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatMapper {
    public Stat toStat(EndpointHitCreateDto endpointHitDto) {
        Stat stat = new Stat();

        stat.setApp(endpointHitDto.getApp());
        stat.setUri(endpointHitDto.getUri());
        stat.setIp(endpointHitDto.getIp());
        stat.setCreated(endpointHitDto.getTimestamp());
        return stat;
    }

    public EndpointHitDto toStatDto(Stat stat) {
        return new EndpointHitDto(
                stat.getId(),
                stat.getApp(),
                stat.getUri(),
                stat.getIp(),
                stat.getCreated()
        );
    }

    public List<ViewStatsDto> toViewStatsDto(List<Object[]> objectsList) {
        List<ViewStatsDto> viewStatsDto = new ArrayList<>();
        for (Object[] vs : objectsList) {
            ViewStatsDto vsDto = new ViewStatsDto();
            vsDto.setApp((String) vs[0]);
            vsDto.setUri((String) vs[1]);
            vsDto.setHits((Long) vs[2]);
            viewStatsDto.add(vsDto);
        }
        return viewStatsDto;
    }
}
