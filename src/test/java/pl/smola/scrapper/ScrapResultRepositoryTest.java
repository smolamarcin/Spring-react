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
import java.time.Month;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ScrapResultRepositoryTest {
    private static final LocalDate DATE = LocalDate.of(2000, Month.APRIL, 1);
    @Autowired
    private ScrapResultRepository repository;

    @Test
    public void save_generatesNonNullId() {
        MirkoScrapResult mirkoScrapResult = new MirkoScrapResult(DATE, ImmutableList.of());

        MirkoScrapResult save = repository.save(mirkoScrapResult);

        assertThat(save).isNotNull();
    }

    @Test
    public void save_persistsRecord() {
        MirkoPost post = MirkoPost.MirkoPostBuilder.aMirkoPost().setImageUrl("url").setAuthor("author")
                .setTags(ImmutableSet.of("tag1", "tag2")).setPostContent("content").setPlusCount(43).build();

        MirkoScrapResult save = repository.save(new MirkoScrapResult(DATE, ImmutableList
                .of(post)));

        List<MirkoPost> posts = repository.findById(save.getId()).get().getPosts();
        assertThat(posts).containsExactly(post);

    }
}