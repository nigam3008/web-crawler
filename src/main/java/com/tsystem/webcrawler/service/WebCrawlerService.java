package com.tsystem.webcrawler.service;

import com.tsystem.webcrawler.dto.CrawlResult;
import com.tsystem.webcrawler.dto.CrawlStatus;

public interface WebCrawlerService {

	String crawl(String url, Integer depth);

	CrawlStatus getStatus(String transactionId);

	CrawlResult fetchResult(String transactionId);
}
