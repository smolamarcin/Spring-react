package pl.smola.scrapper;

import com.google.auto.value.AutoValue;
import org.jsoup.nodes.Document;

import java.time.LocalDate;

@AutoValue
abstract class JsoupDocumentWrapper {
    abstract LocalDate scrapDate();
    abstract Document jsoupDocument();

    public static JsoupDocumentWrapper create(LocalDate scrapDate, Document jsoupDocument) {
        return new AutoValue_JsoupDocumentWrapper(scrapDate, jsoupDocument);
    }


}
