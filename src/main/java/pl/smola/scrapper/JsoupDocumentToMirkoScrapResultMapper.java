package pl.smola.scrapper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;

import static com.google.common.collect.ImmutableList.toImmutableList;

final class JsoupDocumentToMirkoScrapResultMapper {
    private static final String POST_ELEMENTS_CLASS = "entry iC ";
    private static final String TAG_SUMMARY_CLASS = "showTagSummary";
    private static final String PROFILE_SUMMARY_CLASS = "showProfileSummary";
    private static final String PLUS_COUNT_CLASS = "c2a7017";
    private static final String POST_CONTENT_CLASS = "text ";
    private static final String PARAGRAPH_SELECTOR = "p";
    private static final String IMAGE_URL_CLASS = "media-content";

    static MirkoScrapResult map(JsoupDocumentWrapper jsoupDocumentWrapper) {
        Elements postsElements = jsoupDocumentWrapper.getJsoupDocument().getElementsByClass(POST_ELEMENTS_CLASS);
        ImmutableList<MirkoPost> collect = postsElements
                .stream()
                .map(JsoupDocumentToMirkoScrapResultMapper::parse)
                .collect(toImmutableList());
        return new MirkoScrapResult(jsoupDocumentWrapper.getScrapDate(), collect);
    }

    private static MirkoPost parse(Element element) {
        return MirkoPost.MirkoPostBuilder.aMirkoPost()
                .setPlusCount(getPlusCount(element))
                .setPostContent(getPostContent(element))
                .setTags(getTags(element))
                .setAuthor(getAuthor(element))
                .setImageUrl(getImageUrl(element))
                .build();
    }

    private static String getImageUrl(Element element) {
        return Optional.ofNullable(element.getElementsByClass(IMAGE_URL_CLASS).first())
                .map(e -> e.select("a")).map(e -> e.attr("href")).orElse("");
    }

    private static ImmutableSet<String> getTags(Element element) {
        return Optional.ofNullable(element.getElementsByClass(TAG_SUMMARY_CLASS))
                .map(Elements::text)
                .filter(tags -> !tags.isEmpty())
                .map(asString -> asString.split(" "))
                .map(ImmutableSet::copyOf)
                .orElseGet(ImmutableSet::of);

    }

    private static String getPostContent(Element element) {
        return Optional.ofNullable(element.getElementsByClass(POST_CONTENT_CLASS).first())
                .flatMap(e -> Optional.ofNullable(e.select(PARAGRAPH_SELECTOR).first()).map(Element::text))
                .orElse("");
    }

    private static String getAuthor(Element element) {
        return Optional.ofNullable(element.getElementsByClass(PROFILE_SUMMARY_CLASS).first())
                .map(Element::text)
                .orElse("");
    }

    private static int getPlusCount(Element element) {
        return Optional.ofNullable(element.getElementsByClass(PLUS_COUNT_CLASS).first())
                .map(Element::text)
                .map(e -> e.replace("+", ""))
                .filter(e -> !e.isEmpty())
                .map(Integer::valueOf)
                .orElse(0);
    }
}
