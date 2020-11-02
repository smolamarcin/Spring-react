package pl.smola.scrapper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ScrapResultRepositoryTest {
    private static final LocalDateTime DATE_TIME = LocalDateTime
            .of(LocalDate.of(2000, Month.APRIL, 1), LocalTime.MIDNIGHT);
    @Autowired
    private ScrapResultRepository repository;

    @Test
    public void save_generatesNonNullId() {
        MirkoScrapResult mirkoScrapResult = new MirkoScrapResult(DATE_TIME, ImmutableList.of());

        MirkoScrapResult save = repository.save(mirkoScrapResult);

        assertThat(save).isNotNull();
    }

    @Test
    public void save_persistsRecord() {
        MirkoPost post = MirkoPost.MirkoPostBuilder.aMirkoPost().setImageUrl("url").setAuthor("author")
                .setTags(ImmutableSet.of("tag1", "tag2")).setPostContent("content").setPlusCount(43).build();

        MirkoScrapResult save = repository
                .save(new MirkoScrapResult(DATE_TIME, ImmutableList
                        .of(post)));

        List<MirkoPost> posts = repository.findById(save.getId()).get().getPosts();
        assertThat(posts).containsExactly(post);

    }

    @Test
    public void findByDateBeetween_returnsRecordsFromLastHour() {
        MirkoScrapResult scrapOneHourAgo = new MirkoScrapResult(DATE_TIME.minusHours(1), ImmutableList
                .of());
        MirkoScrapResult scrapTwoHoursAgo = new MirkoScrapResult(DATE_TIME.minusHours(2), ImmutableList
                .of());

        ImmutableList<MirkoScrapResult> toSave = ImmutableList
                .of(scrapOneHourAgo, scrapTwoHoursAgo);
        repository.saveAll(toSave);

        assertThat(repository.findByScrapDateTimeBetween(DATE_TIME.minusHours(1),DATE_TIME))
                .containsExactly(scrapOneHourAgo);

    }
}