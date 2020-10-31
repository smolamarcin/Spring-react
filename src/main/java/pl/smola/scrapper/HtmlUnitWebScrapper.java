package pl.smola.scrapper;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;

import java.io.IOException;
import java.io.InputStream;

final class HtmlUnitWebScrapper implements WebScrapper {

    @Override
    public WebPage apply(String url) {
        try (final WebClient webClient = new WebClient()) {
            try {
                Page page = webClient.getPage(url);
                InputStream contentAsStream = page.getWebResponse().getContentAsStream();
                return WebPage.create(url, new byte[]{});
            } catch (IOException e) {
                //todo: throw better exception
                throw new RuntimeException(e);
            }
        }
    }
}
