package pl.smola.scrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

@Component
final class HtmlProvider implements Function<String, Optional<JsoupDocumentWrapper>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlProvider.class);

    @Override
    public Optional<JsoupDocumentWrapper> apply(String url) {
        try {
            return Optional.of(JsoupDocumentWrapper.create(LocalDate.now(), getDocument(url)));
        } catch (IOException e) {
            LOGGER.error(String.format("Unable to fetch %s.", url), e);
            return Optional.empty();
        }
    }

    private Document getDocument(String url) throws IOException {
        return Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
    }
}
