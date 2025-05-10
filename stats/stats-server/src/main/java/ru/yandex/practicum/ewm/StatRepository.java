package ru.yandex.practicum.ewm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.ewm.model.Stat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {

    @Query(value = "SELECT app, uri, count(uri) as hits FROM stat WHERE created >= '2020-05-05T00:00:01' AND created <= '2035-05-05T00:00:01' GROUP BY app, uri ORDER BY hits DESC", nativeQuery = true)
    List<Object[]> getByAllUrisNotUnique(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT app, uri, count(uri) as hits FROM (SELECT app, uri, count(uri) FROM stat WHERE created >= ?1 AND created <= ?2 GROUP BY app, uri) GROUP BY app, uri ORDER BY hits DESC", nativeQuery = true)
    List<Object[]> getByAllUrisUnique(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT app, uri, count(uri) as hits FROM stat WHERE created >= ?1 AND created <= ?2 AND uri IN ( ?3 ) GROUP BY app, uri ORDER BY hits DESC", nativeQuery = true)
    List<Object[]> getGroupUrisNotUnique(LocalDateTime start, LocalDateTime end, Set<String> uris);

    @Query(value = "SELECT app, uri, count(uri) as hits FROM (SELECT app, uri, count(uri) FROM stat WHERE created >= ?1 AND created <= ?2 AND uri IN ( ?3 ) GROUP BY app, uri) GROUP BY app, uri ORDER BY hits DESC", nativeQuery = true)
    List<Object[]> getGroupUrisUnique(LocalDateTime start, LocalDateTime end, Set<String> uris);
}
