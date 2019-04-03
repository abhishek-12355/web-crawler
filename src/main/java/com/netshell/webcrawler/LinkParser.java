
package com.netshell.webcrawler;

import com.netshell.webcrawler.model.Page;

import java.util.Optional;

public interface LinkParser {

    Optional<Page> parse(String pageAddress);

}
