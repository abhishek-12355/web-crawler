package com.netshell.webcrawler;

import com.netshell.webcrawler.repository.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;

public class WebCrawler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebCrawler.class);

    private final LinkParser linkParser;
    private final String startAddress;

    private final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    private final PageRepository pageRepository = new PageRepository();

    public WebCrawler(LinkParser linkParser, String startAddress) {
        this.linkParser = Objects.requireNonNull(linkParser);
        this.startAddress = startAddress;
    }

    public LinkParser getLinkParser() {
        return linkParser;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public Collection<String> getVisitedPages() {
        return pageRepository.getVisitedPages();
    }

    public Collection<String> getErroredPages() {
        return pageRepository.getErroredPages();
    }

    public Collection<String> getSkippedPages() {
        return pageRepository.getSkippedPages();
    }

    public void crawl() {
        LOGGER.info("Crawling with start address: {}", startAddress);
        final CrawlerAction crawlerAction = new CrawlerAction(pageRepository, linkParser, startAddress);
        forkJoinPool.invoke(crawlerAction);
        LOGGER.info("Crawling complete with start address: {}", startAddress);
    }
}
