package com.netshell.webcrawler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Page {

    private String address;
    private final List<String> links = new ArrayList<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(address, page.address) &&
                Objects.equals(links, page.links);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, links);
    }

    @Override
    public String toString() {
        return "Page{" +
                "address='" + address + '\'' +
                ", links=" + links +
                '}';
    }
}
