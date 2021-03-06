
package com.netshell.webcrawler;

import com.netshell.webcrawler.exception.DuplicatePageException;
import com.netshell.webcrawler.exception.PageNotFoundException;
import com.netshell.webcrawler.model.Page;
import com.netshell.webcrawler.repository.PageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;

public class CrawlerAction extends RecursiveAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerAction.class);

    private final PageRepository pageRepository;
    private final LinkParser parser;
    private final String pageAddress;

    private final Object object = new Object();

    CrawlerAction(PageRepository pageRepository, LinkParser parser, String pageAddress) {
        this.pageRepository = Objects.requireNonNull(pageRepository);
        this.parser = Objects.requireNonNull(parser);

        if (pageAddress == null || pageAddress.isEmpty()) {
            throw new IllegalArgumentException("pageAddress");
        }

        this.pageAddress = pageAddress;
    }

    @Override
    protected void compute() {
        LOGGER.debug("Invoking crawler action with address: {}", pageAddress);

        if (pageRepository.isPageVisited(pageAddress)) {
            pageRepository.addSkippedPage(pageAddress);
            return;
        }

        try {
            final Optional<Page> page = parser.parse(pageAddress);
            if (!page.isPresent()) {
                LOGGER.error("Page Not Found: {}", pageAddress);
                pageRepository.addErroredPage(pageAddress);
                return;
            }

            final List<String> links = page.get().getLinks();
            final List<CrawlerAction> actions = links.stream()
                    .map(p -> new CrawlerAction(pageRepository, parser, p))
                    .collect(Collectors.toList());

            synchronized (object) {
                if (pageRepository.isPageVisited(pageAddress)) {
                    pageRepository.addSkippedPage(pageAddress);
                    return;
                }
                pageRepository.addPage(pageAddress);
            }
            invokeAll(actions);
        } catch (DuplicatePageException e) {
            LOGGER.error("Not able to process page: {}-{}", e.getClass().getSimpleName(), e.getMessage());
            pageRepository.addSkippedPage(pageAddress);
        }
    }
}
