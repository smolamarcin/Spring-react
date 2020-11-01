package pl.smola.scrapper;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.time.LocalDate;
import java.util.function.Function;

final class JsoupWebPageProvider implements Function<String, JsoupDocumentWrapper> {

    @Override
    public JsoupDocumentWrapper apply(String url) {
        try {
            return JsoupDocumentWrapper.create(LocalDate.now(),Jsoup.connect(url).get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
