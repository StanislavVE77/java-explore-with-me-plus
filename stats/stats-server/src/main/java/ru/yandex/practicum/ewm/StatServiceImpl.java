package ru.yandex.practicum.ewm;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.ewm.mapper.StatMapper;
import ru.yandex.practicum.ewm.model.Stat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    @Autowired
    private final StatRepository statRepository;
    private final StatMapper mapper;

    @Override
    public EndpointHitDto createStat(EndpointHitCreateDto endpointHitCreateDto) {
        Stat stat = mapper.toStat(endpointHitCreateDto);
        stat = statRepository.save(stat);
        return mapper.toStatDto(stat);
    }

    @Override
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique) {
        //List<ViewStatsDto> viewStatsDto = new ArrayList<>();
        List<Object[]> viewStats;
        if (uris == null) {
            if (unique == false) {
                viewStats = statRepository.getByAllUrisNotUnique(start, end);
            } else {
                viewStats = statRepository.getByAllUrisUnique(start, end);
            }
        } else {
            if (unique == false) {
                viewStats = statRepository.getGroupUrisNotUnique(start, end, uris);
            } else {
                viewStats = statRepository.getGroupUrisUnique(start, end, uris);
            }

        }
        return mapper.toViewStatsDto(viewStats);
    }
}
