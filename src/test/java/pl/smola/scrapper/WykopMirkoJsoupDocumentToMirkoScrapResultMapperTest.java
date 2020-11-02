package pl.smola.scrapper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static com.google.common.truth.Truth.assertThat;

class WykopMirkoJsoupDocumentToMirkoScrapResultMapperTest {
    private static final MirkoPost FIRST_EXPECTED_POST = MirkoPost.MirkoPostBuilder.aMirkoPost()
            .setPlusCount(43)
            .setAuthor("Adams_GA")
            .setPostContent("To było jak strzał do pustej bramki #f1")
            .setTags(ImmutableSet.of("f1"))
            .setImageUrl("https://www.wykop.pl/cdn/c3201142/comment_1604165933yMdoENzJCfrAHo1Fl1RzFa.jpg")
            .build();
    private static final MirkoPost SECOND_EXPECTED_POST = MirkoPost.MirkoPostBuilder.aMirkoPost()
            .setPlusCount(27)
            .setAuthor("zjadlbym_kebaba")
            .setPostContent("Nowy przekaz partyjny? #neuropa #bekazpisu #protest")
            .setTags(ImmutableSet.of("neuropa", "bekazpisu", "protest"))
            .setImageUrl("https://www.wykop.pl/cdn/c3201142/comment_1604166389ohWlqNlCgjdQNO2AEtyaoM.jpg")
            .build();
    private static final LocalDateTime DATE_TIME = LocalDateTime
            .of(LocalDate.of(2000, Month.NOVEMBER, 10), LocalTime.MIDNIGHT);
    private static final MirkoScrapResult DEFAULT_INSTANCE = new MirkoScrapResult(DATE_TIME, ImmutableList.of());

    private final Document testDocument = loadDocument("src/test/resources/mirko.html");
    private final Document brokenDocument = loadDocument("src/test/resources/broken_mirko.html");


    @Test
    public void map_returnsNotNullObject() {
        MirkoScrapResult mirkoScrapResult = JsoupDocumentToMirkoScrapResultMapper
                .map(JsoupDocumentWrapper.create(DATE_TIME, testDocument));

        assertThat(mirkoScrapResult).isNotNull();
        assertThat(mirkoScrapResult.getScrapDateTime()).isEqualTo(DATE_TIME);
    }

    @Test
    public void map_fetchesProperAttributes() {
        MirkoScrapResult mirkoScrapResult = JsoupDocumentToMirkoScrapResultMapper
                .map(JsoupDocumentWrapper.create(DATE_TIME, testDocument));

        assertThat(mirkoScrapResult.getPosts())
                .containsAtLeast(FIRST_EXPECTED_POST, SECOND_EXPECTED_POST);
    }

    @Test
    public void map_emptyDocument_returnsDefaultInstance() {

        MirkoScrapResult mirkoScrapResult = JsoupDocumentToMirkoScrapResultMapper
                .map(JsoupDocumentWrapper.create(DATE_TIME, brokenDocument));

        assertThat(mirkoScrapResult).isEqualTo(DEFAULT_INSTANCE);
    }

    private static Document loadDocument(String path) {
        File htmlFile = new File(path);
        try {
            return Jsoup.parse(htmlFile, "UTF-8", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}