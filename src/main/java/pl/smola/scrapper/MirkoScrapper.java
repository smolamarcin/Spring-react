package pl.smola.scrapper;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableList.toImmutableList;


@Component
final class MirkoScrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MirkoScrapper.class);
    private static final int PAGES_COUNT = 10;
    private final HtmlProvider webPageProvider;
    private final ScrapResultRepository mirkoScrapResultRepository;

    @Autowired
    MirkoScrapper(HtmlProvider webPageProvider, ScrapResultRepository
            mirkoScrapResultRepository) {
        this.webPageProvider = webPageProvider;
        this.mirkoScrapResultRepository = mirkoScrapResultRepository;
    }

    /**
     * Runs every 1 hour.
     */
    @Scheduled(cron = "0 * * * * *")
    public void scrapLastOneHoursHottestPosts() {
        scrapMirko(1);
    }

    /**
     * Runs every 12 hours.
     */
    @Scheduled(cron = "0 */12 * * *")
    public void scrapLastTwelveHoursHottestPosts() {
        scrapMirko(12);
    }

    /**
     * Runs ever day at 8 am.
     */
    @Scheduled(cron = "0 8 * * *")
    public void scrapLastDayHottestPosts() {
        scrapMirko(24);
    }


    private ImmutableList<MirkoScrapResult> scrapMirko(int timeOrSomething) {
        ImmutableList<MirkoScrapResult> result = IntStream.rangeClosed(1, PAGES_COUNT)
                .mapToObj(page -> String
                        .format("https://www.wykop.pl/mikroblog/hot/ostatnie/%d/strona/%d", timeOrSomething, page))
                .peek(url -> LOGGER.info("Scrapping " + url))
                .map(webPageProvider)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(JsoupDocumentToMirkoScrapResultMapper::map)
                .map(mirkoScrapResultRepository::save)
                .collect(toImmutableList());
        LOGGER.info(String.format("Scrapped %d results.", result.size()));
        return result;
    }
}
