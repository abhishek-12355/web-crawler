package com.netshell.webcrawler.repository;

import com.netshell.webcrawler.exception.DuplicatePageException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class PageRepository {

    private final Collection<String> visitedPages = new HashSet<>();
    private final Collection<String> erroredPages = new HashSet<>();
    private final Collection<String> skippedPages = new HashSet<>();

    public Collection<String> getVisitedPages() {
        return Collections.unmodifiableCollection(visitedPages);
    }

    public Collection<String> getErroredPages() {
        return Collections.unmodifiableCollection(erroredPages);
    }

    public Collection<String> getSkippedPages() {
        return Collections.unmodifiableCollection(skippedPages);
    }

    public void addPage(String pageAddress) throws DuplicatePageException {
        if (isPageVisited(pageAddress)) {
            throw new DuplicatePageException(pageAddress);
        }

        visitedPages.add(pageAddress);
    }

    public void addErroredPage(String pageAddress) {
        erroredPages.add(pageAddress);
    }

    public void addSkippedPage(String pageAddress) {
        skippedPages.add(pageAddress);
    }

    public boolean isPageVisited(String pageAddress) {
        return visitedPages.contains(pageAddress) || erroredPages.contains(pageAddress);
    }
}
