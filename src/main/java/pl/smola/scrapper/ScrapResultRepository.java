package pl.smola.scrapper;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface ScrapResultRepository extends CrudRepository<MirkoScrapResult, Long> {
    Iterable<MirkoScrapResult> findByScrapDateTimeBetween(LocalDateTime from, LocalDateTime to);

}
