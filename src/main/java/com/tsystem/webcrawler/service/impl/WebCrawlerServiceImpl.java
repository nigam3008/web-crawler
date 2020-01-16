package com.tsystem.webcrawler.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsystem.webcrawler.dto.CrawlResult;
import com.tsystem.webcrawler.dto.CrawlStatus;
import com.tsystem.webcrawler.dto.Page;
import com.tsystem.webcrawler.entity.UrlCrawlInfoEntity;
import com.tsystem.webcrawler.entity.UrlTransactionEntity;
import com.tsystem.webcrawler.exception.NoSuchTransactionException;
import com.tsystem.webcrawler.repo.UrlCrawlInfoRepo;
import com.tsystem.webcrawler.repo.UrlTransactionRepo;
import com.tsystem.webcrawler.service.UrlCrawlerService;
import com.tsystem.webcrawler.service.WebCrawlerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebCrawlerServiceImpl implements WebCrawlerService {
	
	@Autowired
	private UrlTransactionRepo urlTransactionRepo;
	
	@Autowired
	private UrlCrawlerService urlCrawlerService;
	
	@Autowired
	private UrlCrawlInfoRepo urlCrawlInfoRepo;

	public String crawl(String url, Integer depth) {
		log.info("Run crawl for url: {} with depth: {}", url, depth);
		UrlTransactionEntity urlTransactionEntity = new UrlTransactionEntity(url, UUID.randomUUID().toString(), CrawlStatus.SUBMITTED, depth);
		urlTransactionEntity = urlTransactionRepo.save(urlTransactionEntity);
		urlCrawlerService.startCrawling(urlTransactionEntity);
		return urlTransactionEntity.getTransactionId();
	}

	public CrawlStatus getStatus(String transactionId) {
		UrlTransactionEntity urlTransaction = urlTransactionRepo.findByTransactionId(transactionId);
		if(null == urlTransaction) {
			throw new NoSuchTransactionException(transactionId);
		}
		return urlTransaction.getStatus();
	}

	public CrawlResult fetchResult(String transactionId) {
		UrlTransactionEntity urlTransaction = urlTransactionRepo.findByTransactionId(transactionId);
		if(null == urlTransaction) {
			throw new NoSuchTransactionException(transactionId);
		}
		List<UrlCrawlInfoEntity> urlCrawlInfoEntities = urlCrawlInfoRepo.findByTransactionId(transactionId);
		Set<Page> pages = new HashSet<>();
		int totalImages = 0;
		for(UrlCrawlInfoEntity crawlInfoEntity : urlCrawlInfoEntities) {
			totalImages += crawlInfoEntity.getImageCount();
			pages.add(new Page(crawlInfoEntity.getPageTitle(), crawlInfoEntity.getUrl(), crawlInfoEntity.getImageCount()));
		}
		return new CrawlResult(urlCrawlInfoEntities.size(), totalImages, pages);
	}
}
