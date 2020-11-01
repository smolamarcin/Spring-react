package pl.smola.scrapper;

import com.google.common.collect.ImmutableList;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;

import static com.google.common.collect.ImmutableList.toImmutableList;

final class WykopMirkoJsoupDocumentToSomePojoMapper {
    static MirkoScrapResult map(JsoupDocumentWrapper jsoupDocumentWrapper) {
        Elements postsElements = jsoupDocumentWrapper.jsoupDocument().getElementsByClass("entry iC ");
        return MirkoScrapResult.create(jsoupDocumentWrapper.scrapDate(), postsElements
                .stream()
                .map(WykopMirkoJsoupDocumentToSomePojoMapper::parse)
                .collect(toImmutableList()));
    }

    private static MirkoPost parse(Element element) {
        return MirkoPost.builder()
                .plusCount(getPlusCount(element))
                .postContent(getPostContent(element))
                .tags(getTags(element))
                .author(getAuthor(element))
                .build();
    }

    private static ImmutableList<String> getTags(Element element) {
        return Optional.ofNullable(element.getElementsByClass("showTagSummary"))
                .map(Elements::text)
                .filter(tags -> !tags.isEmpty())
                .map(asString -> asString.split(" "))
                .map(ImmutableList::copyOf)
                .orElseGet(ImmutableList::of);

    }

    private static String getPostContent(Element element) {
        return Optional.ofNullable(element.getElementsByClass("text ").first())
                .flatMap(e -> Optional.ofNullable(e.select("p").first()).map(Element::text))
                .orElse("");
    }

    private static String getAuthor(Element element) {
        return Optional.ofNullable(element.getElementsByClass("showProfileSummary").first())
                .map(Element::text)
                .orElse("");
    }

    private static int getPlusCount(Element element) {
        return Optional.ofNullable(element.getElementsByClass("c2a7017").first())
                .map(Element::text)
                .map(e -> e.replace("+", ""))
                .filter(e -> !e.isEmpty())
                .map(Integer::valueOf)
                .orElse(0);
    }
}
