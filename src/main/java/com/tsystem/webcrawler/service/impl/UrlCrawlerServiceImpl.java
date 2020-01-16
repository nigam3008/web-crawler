package com.tsystem.webcrawler.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.tsystem.webcrawler.dto.CrawlStatus;
import com.tsystem.webcrawler.dto.PageDetail;
import com.tsystem.webcrawler.entity.UrlCrawlInfoEntity;
import com.tsystem.webcrawler.entity.UrlTransactionEntity;
import com.tsystem.webcrawler.repo.UrlCrawlInfoRepo;
import com.tsystem.webcrawler.repo.UrlTransactionRepo;
import com.tsystem.webcrawler.service.UrlCrawlerService;
import com.tsystem.webcrawler.strategy.SameWebsiteOnlyStrategy;
import com.tsystem.webcrawler.util.WebCrawlerUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UrlCrawlerServiceImpl implements UrlCrawlerService {

	@Autowired
	private UrlTransactionRepo urlTransactionRepo;

	@Autowired
	private UrlCrawlInfoRepo urlCrawlInfoRepo;

	@Async
	public void startCrawling(UrlTransactionEntity urlTransaction) {
		try {
			urlTransaction.setStatus(CrawlStatus.IN_PROGRESS);
			urlTransactionRepo.save(urlTransaction);
			performCrawl(urlTransaction);
		} catch (Exception e) {
			log.error("Error occured while running crawl for {}", urlTransaction);
			urlTransaction.setStatus(CrawlStatus.FAILED);
			urlTransactionRepo.save(urlTransaction);
		}
	}

	@Async
	private void performCrawl(UrlTransactionEntity urlTransaction) {
		try {
			int depth = urlTransaction.getDepth();
			String transactionId = urlTransaction.getTransactionId();
			Set<String> processedUrls = new HashSet<>();
			Set<String> pendingUrls = new HashSet<String>();
			pendingUrls.add(urlTransaction.getUrl());
			while (depth-- >= 0) {
				Set<String> newUrls = new HashSet<String>();
				for(String currentUrl : pendingUrls) {
					if (processedUrls.contains(currentUrl)) {
						continue;
					} else {
						processedUrls.add(currentUrl);
					}
					AsyncResult<Optional<PageDetail>> pageDetailAsync = getPageDetail(currentUrl, transactionId);
					Optional<PageDetail> pageDetailOpt = pageDetailAsync.get();
					if(pageDetailOpt.isPresent()) {
						newUrls = pageDetailOpt.get().getLinks().stream().map(link -> link.attr("abs:href")).collect(Collectors.toSet());
					}
				}
				pendingUrls = newUrls;
			}
			urlTransaction.setStatus(CrawlStatus.PROCESSED);
			urlTransactionRepo.save(urlTransaction);
		} catch (Exception ex) {
			log.error("Error occured while running crawl for {}", urlTransaction);
			urlTransaction.setStatus(CrawlStatus.FAILED);
			urlTransactionRepo.save(urlTransaction);
		}
	}

	@Async("threadPoolTaskExecutor")
	private AsyncResult<Optional<PageDetail>> getPageDetail(String url, String transactionId) {
		Optional<PageDetail> pageDetailOpt = null;
		if (!WebCrawlerUtil.isValidURL(url, new SameWebsiteOnlyStrategy(url))) {
			log.info("Skip url {}", url);
			pageDetailOpt = Optional.empty();
		} else {
			PageDetail pageDetail = WebCrawlerUtil.crawl(url);
			urlCrawlInfoRepo.save(
					new UrlCrawlInfoEntity(url, pageDetail.getImageCount(), pageDetail.getTitle(), transactionId));
			pageDetailOpt = Optional.of(pageDetail);
		}
		return new AsyncResult<Optional<PageDetail>>(pageDetailOpt);
	}
}
