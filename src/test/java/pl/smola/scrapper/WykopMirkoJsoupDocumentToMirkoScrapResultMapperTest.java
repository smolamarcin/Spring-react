package pl.smola.scrapper;

import com.google.common.collect.ImmutableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import static com.google.common.truth.Truth.assertThat;

class WykopMirkoJsoupDocumentToMirkoScrapResultMapperTest {
    private static final MirkoPost FIRST_EXPECTED_POST = MirkoPost.builder()
            .plusCount(43)
            .author("Adams_GA")
            .postContent("To było jak strzał do pustej bramki #f1")
            .tags(ImmutableList.of("f1"))
            .build();
    private static final MirkoPost SECOND_EXPECTED_POST = MirkoPost.builder()
            .plusCount(27)
            .author("zjadlbym_kebaba")
            .postContent("Nowy przekaz partyjny? #neuropa #bekazpisu #protest")
            .tags(ImmutableList.of("neuropa", "bekazpisu", "protest"))
            .build();

    private static final LocalDate DATE = LocalDate.of(2000, Month.NOVEMBER, 10);
    private final Document testDocument = getTestDocument();

    private Document getTestDocument() {
        File htmlFile = new File("src/test/resources/mirko.html");
        try {
            return Jsoup.parse(htmlFile, "UTF-8", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void map_returnsNotNullObject() {
        MirkoScrapResult mirkoScrapResult = WykopMirkoJsoupDocumentToSomePojoMapper
                .map(JsoupDocumentWrapper.create(DATE, testDocument));

        assertThat(mirkoScrapResult).isNotNull();
        assertThat(mirkoScrapResult.scrapDate()).isEqualTo(DATE);
    }

    @Test
    public void map_fetchesProperAttributes() {
        MirkoScrapResult mirkoScrapResult = WykopMirkoJsoupDocumentToSomePojoMapper
                .map(JsoupDocumentWrapper.create(DATE, testDocument));

        assertThat(mirkoScrapResult.posts())
                .containsAtLeast(FIRST_EXPECTED_POST, SECOND_EXPECTED_POST);
    }
}