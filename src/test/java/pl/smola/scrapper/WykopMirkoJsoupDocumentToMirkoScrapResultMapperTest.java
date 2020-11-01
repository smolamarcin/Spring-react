package pl.smola.scrapper;

import com.google.common.collect.ImmutableList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

class WykopMirkoJsoupDocumentToMirkoScrapResultMapperTest {
    private static final MirkoPost FIRST_EXPECTED_POST = MirkoPost.builder()
            .plusCount(43)
            .author("Adams_GA")
            .postContent("To było jak strzał do pustej bramki #f1")
            .tags(ImmutableList.of("f1"))
            .maybeImageUrl(Optional
                    .of("https://www.wykop.pl/cdn/c3201142/comment_1604165933yMdoENzJCfrAHo1Fl1RzFa.jpg"))
            .build();
    private static final MirkoPost SECOND_EXPECTED_POST = MirkoPost.builder()
            .plusCount(27)
            .author("zjadlbym_kebaba")
            .postContent("Nowy przekaz partyjny? #neuropa #bekazpisu #protest")
            .tags(ImmutableList.of("neuropa", "bekazpisu", "protest"))
            .maybeImageUrl(Optional
                    .of("https://www.wykop.pl/cdn/c3201142/comment_1604166389ohWlqNlCgjdQNO2AEtyaoM.jpg"))
            .build();
    private static final LocalDate DATE = LocalDate.of(2000, Month.NOVEMBER, 10);
    private static final MirkoScrapResult DEFAULT_INSTANCE = MirkoScrapResult.create(DATE, ImmutableList.of());

    private final Document testDocument = loadDocument("src/test/resources/mirko.html");
    private final Document brokenDocument = loadDocument("src/test/resources/broken_mirko.html");


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

    @Test
    public void map_emptyDocument_returnsDefaultInstance() {

        MirkoScrapResult mirkoScrapResult = WykopMirkoJsoupDocumentToSomePojoMapper
                .map(JsoupDocumentWrapper.create(DATE, brokenDocument));

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