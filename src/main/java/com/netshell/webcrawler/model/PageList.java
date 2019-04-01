package com.netshell.webcrawler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PageList {

    private final List<Page> pages = new ArrayList<>();

    public List<Page> getPages() {
        return pages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageList pageList = (PageList) o;
        return Objects.equals(pages, pageList.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pages);
    }

    @Override
    public String toString() {
        return "PageList{" +
                "pages=" + pages +
                '}';
    }
}
