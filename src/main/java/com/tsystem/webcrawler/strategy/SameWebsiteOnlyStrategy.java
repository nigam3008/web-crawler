package com.tsystem.webcrawler.strategy;

public class SameWebsiteOnlyStrategy implements URLFilterStategy {

    protected String domainUrl;

    public SameWebsiteOnlyStrategy(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public boolean include(String url) {
        return url.startsWith(this.domainUrl);
    }
}
