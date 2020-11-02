package pl.smola.scrapper;

import lombok.Value;
import org.jsoup.nodes.Document;

import java.time.LocalDateTime;

@Value(staticConstructor = "create")
final class JsoupDocumentWrapper {
    private final LocalDateTime scrapDateTime;

    private final Document jsoupDocument;


}
