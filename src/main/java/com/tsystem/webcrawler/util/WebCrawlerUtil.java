package com.tsystem.webcrawler.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.tsystem.webcrawler.config.InvalidWebCrawlerFormats;
import com.tsystem.webcrawler.dto.PageDetail;
import com.tsystem.webcrawler.exception.CrawlFailedException;
import com.tsystem.webcrawler.strategy.URLFilterStategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebCrawlerUtil {

	
	public static PageDetail crawl(final String url) {
        log.info("Crawling url: {}", url);
        try {
            final Document doc = Jsoup.connect(url).timeout(5000)
                    .followRedirects(true).get();
            final Elements links = doc.select("a[href]");
            final Elements images = doc.select("img");
            log.debug("Fetched links[{}] for url: {}", links, url);
            return new PageDetail(url, images.size(), links, doc.title());
        } catch (final IOException | IllegalArgumentException e) {
            log.error(String.format("Error getting contents of url %s", url), e);
            throw new CrawlFailedException("Crawl failed for url " + url);
        }
    }
	
	public static boolean isValidURL(String nextUrl, URLFilterStategy strategy) {
		if(strategy != null && !strategy.include(nextUrl)){
			return false;
		}
		if(nextUrl.startsWith("javascript:"))  { return false; }
		if(nextUrl.startsWith("#"))            { return false; }
		for(InvalidWebCrawlerFormats format: InvalidWebCrawlerFormats.values()) {
			if(nextUrl.endsWith(format.toString())){
				return false;
			}
		}
		return true;
	}
}
