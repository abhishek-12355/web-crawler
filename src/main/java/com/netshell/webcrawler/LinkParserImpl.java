
package com.netshell.webcrawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netshell.webcrawler.exception.PageNotFoundException;
import com.netshell.webcrawler.model.Page;
import com.netshell.webcrawler.model.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class LinkParserImpl implements LinkParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkParserImpl.class);

    private final Map<String, Page> pageMap;

    public LinkParserImpl(String resourcePath) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final URL resource = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        final PageList pageList = mapper.readValue(resource, PageList.class);

        pageMap = pageList.getPages().stream()
                .collect(Collectors.toConcurrentMap(Page::getAddress, p -> p));
    }

    @Override
    public Optional<Page> parse(String pageAddress) {
        LOGGER.debug("Trying to find page: {}", pageAddress);
        return Optional.ofNullable(pageMap.get(pageAddress));
    }
}
