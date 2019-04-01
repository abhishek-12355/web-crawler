
package com.netshell.webcrawler.exception;

public class DuplicatePageException extends Exception {
    public DuplicatePageException(String pageAddress) {
        super(pageAddress);
    }
}
