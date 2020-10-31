package pl.smola.scrapper;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class WebPage {
    abstract String url();

    abstract byte[] body();

    public static WebPage create(String url, byte[] body) {
        return new AutoValue_WebPage(url, body);
    }

}

