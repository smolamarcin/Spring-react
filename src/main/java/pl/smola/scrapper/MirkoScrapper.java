package pl.smola.scrapper;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.google.common.collect.ImmutableList.toImmutableList;


@Component
final class MirkoScrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MirkoScrapper.class);
    private static final int PAGES_COUNT = 10;
    private static final String MIRKO_ROOT_URL_TEMPLATE = "https://www.wykop.pl/mikroblog/hot/ostatnie/%d/strona/%d";

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
    @Scheduled(cron = "0 */12 * * * *")
    public void scrapLastTwelveHoursHottestPosts() {
        scrapMirko(12);
    }

    /**
     * Runs ever day at 8 am.
     */
    @Scheduled(cron = "0 8 * * * *")
    public void scrapLastDayHottestPosts() {
        scrapMirko(24);
    }


    private ImmutableList<MirkoScrapResult> scrapMirko(int hours) {
        LocalDateTime now = LocalDateTime.now();
        ImmutableList<MirkoScrapResult> scrappedBefore = scrappedBefore(now);
        ImmutableList<MirkoScrapResult> results = ImmutableList
                .copyOf(mirkoScrapResultRepository.saveAll(IntStream.rangeClosed(1, PAGES_COUNT)
                        .mapToObj(page -> buildUrl(hours, page))
                        .peek(url -> LOGGER.info("Scrapping {}.", url))
                        .map(webPageProvider)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(JsoupDocumentToMirkoScrapResultMapper::map)
                        .filter(mirkoScrapResult -> !isScrappedBefore(scrappedBefore, mirkoScrapResult))
                        .collect(toImmutableList())));
        LOGGER.info("Scrapped {} posts.", getPostsCount(results));
        return results;
    }

    private long getPostsCount(ImmutableList<MirkoScrapResult> results) {
         return results.stream()
                .map(MirkoScrapResult::getPosts)
                .mapToLong(Collection::size)
                .sum();
    }

    private static String buildUrl(int hours, int page) {
        return String
                .format(MIRKO_ROOT_URL_TEMPLATE, hours, page);
    }

    private ImmutableList<MirkoScrapResult> scrappedBefore(LocalDateTime now) {
        return ImmutableList.copyOf(mirkoScrapResultRepository
                .findByScrapDateTimeBetween(now.minusDays(1), now));
    }

    private static boolean isScrappedBefore(ImmutableList<MirkoScrapResult> scrappedBefore, MirkoScrapResult
            mirkoScrapResult) {
        return scrappedBefore.contains(mirkoScrapResult);
    }
}
