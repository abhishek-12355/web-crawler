
package com.netshell.webcrawler;

import com.netshell.webcrawler.exception.PageNotFoundException;

import java.util.Collection;

public interface LinkParser {

    Collection<String> getLinks(String pageAddress) throws PageNotFoundException;

}
