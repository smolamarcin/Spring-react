package pl.smola.scrapper;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import java.time.LocalDate;


@AutoValue
abstract class MirkoScrapResult {
    abstract LocalDate scrapDate();

    abstract ImmutableList<MirkoPost> posts();

    static MirkoScrapResult create(LocalDate scrapDate, ImmutableList<MirkoPost> posts) {
        return new AutoValue_MirkoScrapResult(scrapDate, posts);
    }


}
