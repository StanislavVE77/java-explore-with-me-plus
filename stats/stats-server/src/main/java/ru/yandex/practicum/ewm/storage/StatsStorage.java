package ru.yandex.practicum.ewm.storage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.ewm.model.Hit;
import ru.yandex.practicum.ewm.service.StatsQueryService;

import java.util.List;

@Repository
public interface StatsStorage extends CrudRepository<Hit, Integer>, StatsQueryService {

    List<Hit> getStats(String start, String end, List<String> uris);

}
