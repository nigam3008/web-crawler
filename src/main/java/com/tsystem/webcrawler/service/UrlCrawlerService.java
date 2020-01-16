package com.tsystem.webcrawler.service;

import com.tsystem.webcrawler.entity.UrlTransactionEntity;

public interface UrlCrawlerService {

	void startCrawling(UrlTransactionEntity urlTransaction);
}
