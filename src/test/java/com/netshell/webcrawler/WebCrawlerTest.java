package com.netshell.webcrawler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WebCrawlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebCrawlerTest.class);
    private static final String RESOURCE_PATH = "internet_1.json";
    private LinkParserImpl linkParser;

    @Before
    public void setUp() throws Exception {
        linkParser = new LinkParserImpl(RESOURCE_PATH);
    }

    @Test
    public void Test_Page02() {
        final WebCrawler crawler = new WebCrawler(linkParser, "page-02");
        crawler.crawl();
        final Collection<String> visitedPages = crawler.getVisitedPages();
        final Collection<String> erroredPages = crawler.getErroredPages();
        final Collection<String> skippedPages = crawler.getSkippedPages();

        LOGGER.info("Visited: {}", visitedPages.toString());
        LOGGER.info("Skipped: {}", skippedPages.toString());
        LOGGER.info("Error: {}", erroredPages.toString());

        final List<String> expectedVisitedPages = Arrays.asList("page-99", "page-01", "page-04", "page-05", "page-02", "page-03", "page-08", "page-09", "page-06", "page-07");
        final List<String> expectedSkippedPages = Arrays.asList("page-01", "page-10", "page-04", "page-05", "page-02", "page-03", "page-08", "page-09");
        final List<String> expectedErroredPages = Arrays.asList("page-11", "page-00", "page-12", "page-10", "page-13");

        Assert.assertTrue(compareCollection(expectedVisitedPages, visitedPages));
        Assert.assertTrue(compareCollection(expectedSkippedPages, skippedPages));
        Assert.assertTrue(compareCollection(expectedErroredPages, erroredPages));
    }

    @Test
    public void Test_Page96() {
        final WebCrawler crawler = new WebCrawler(linkParser, "page-96");
        crawler.crawl();
        final Collection<String> visitedPages = crawler.getVisitedPages();
        final Collection<String> erroredPages = crawler.getErroredPages();
        final Collection<String> skippedPages = crawler.getSkippedPages();

        LOGGER.info("Visited: {}", visitedPages.toString());
        LOGGER.info("Skipped: {}", skippedPages.toString());
        LOGGER.info("Error: {}", erroredPages.toString());

        final List<String> expectedVisitedPages = Arrays.asList("page-96", "page-99", "page-01", "page-97", "page-98", "page-04", "page-05", "page-02", "page-03", "page-08", "page-09", "page-06", "page-07");
        final List<String> expectedSkippedPages = Arrays.asList("page-99", "page-01", "page-10", "page-04", "page-05", "page-02", "page-03", "page-08", "page-09");
        final List<String> expectedErroredPages = Arrays.asList("page-00", "page-11", "page-12", "page-10", "page-13");

        Assert.assertTrue(compareCollection(expectedVisitedPages, visitedPages));
        Assert.assertTrue(compareCollection(expectedSkippedPages, skippedPages));
        Assert.assertTrue(compareCollection(expectedErroredPages, erroredPages));
    }

    @Test
    public void Test_PageNotExist() {
        final WebCrawler crawler = new WebCrawler(linkParser, "NotExists");
        crawler.crawl();
        final Collection<String> visitedPages = crawler.getVisitedPages();
        final Collection<String> erroredPages = crawler.getErroredPages();
        final Collection<String> skippedPages = crawler.getSkippedPages();

        LOGGER.info("Visited: {}", visitedPages.toString());
        LOGGER.info("Skipped: {}", skippedPages.toString());
        LOGGER.info("Error: {}", erroredPages.toString());

        final List<String> expectedVisitedPages = Collections.emptyList();
        final List<String> expectedSkippedPages = Collections.emptyList();
        final List<String> expectedErroredPages = Collections.singletonList("NotExists");

        Assert.assertTrue(compareCollection(expectedVisitedPages, visitedPages));
        Assert.assertTrue(compareCollection(expectedSkippedPages, skippedPages));
        Assert.assertTrue(compareCollection(expectedErroredPages, erroredPages));
    }

    private static <T> boolean compareCollection(Collection<T> c1, Collection<T> c2) {

//        if (c1.size() != c2.size()) {
//            return false;
//        }

        for (T t : c1) {
            if (!c2.contains(t)) {
                return false;
            }
        }
        return true;
    }
}