
package com.netshell.webcrawler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netshell.webcrawler.exception.PageNotFoundException;
import com.netshell.webcrawler.model.Page;
import com.netshell.webcrawler.model.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class LinkParserImpl implements LinkParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkParserImpl.class);

    private final List<Page> pageList;

    public LinkParserImpl(String resourcePath) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final URL resource = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
        this.pageList = mapper.readValue(resource, PageList.class).getPages();
    }

    @Override
    public Collection<String> getLinks(String pageAddress) throws PageNotFoundException {

        LOGGER.debug("Trying to find page: {}", pageAddress);

        final Page page = pageList.stream()
                .filter(p -> p.getAddress().equals(pageAddress))
                .findAny()
                .orElseThrow(() -> new PageNotFoundException(pageAddress));

        return Collections.unmodifiableCollection(page.getLinks());
    }
}
