package pl.smola.scrapper;

import lombok.Value;
import org.jsoup.nodes.Document;

import java.time.LocalDate;

@Value(staticConstructor = "create")
final class JsoupDocumentWrapper {
    private final LocalDate scrapDate;

    private final Document jsoupDocument;


}
