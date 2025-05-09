package ru.yandex.practicum.ewm.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.ewm.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatsQueryServiceImpl implements StatsQueryService {

    @PersistenceContext
    private final EntityManager entityManager;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<Hit> getStats(String start,
                              String end,
                              List<String> uris) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hit> cq = cb.createQuery(Hit.class);
        Root<Hit> root = cq.from(Hit.class);

        Predicate timePredicate = buildTimePredicate(cb, root, start, end);
        Predicate uriPredicate = buildUriPredicate(cb, root, uris);

        if (uriPredicate != null) {
            cq.where(cb.and(timePredicate, uriPredicate));
        } else {
            cq.where(timePredicate);
        }

        return entityManager.createQuery(cq).getResultList();
    }

    private Predicate buildTimePredicate(CriteriaBuilder cb,
                                         Root<Hit> root,
                                         String start,
                                         String end) {
        LocalDateTime startTime = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endTime = LocalDateTime.parse(end, FORMATTER);
        return cb.between(root.get("timestamp"), startTime, endTime);
    }

    private Predicate buildUriPredicate(CriteriaBuilder cb,
                                        Root<Hit> root,
                                        List<String> uris) {
        if (uris == null || uris.isEmpty()) {
            return null;
        }
        return cb.or(uris.stream()
                .map(uri -> cb.equal(root.get("uri"), uri))
                .toArray(Predicate[]::new));
    }
}
