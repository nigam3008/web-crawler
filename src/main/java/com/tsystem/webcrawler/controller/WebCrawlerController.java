package com.tsystem.webcrawler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tsystem.webcrawler.dto.CrawlResult;
import com.tsystem.webcrawler.dto.CrawlStatus;
import com.tsystem.webcrawler.dto.WebCrawlerRequestDto;
import com.tsystem.webcrawler.service.WebCrawlerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/crawl")
@Slf4j
public class WebCrawlerController {

	@Autowired
	private WebCrawlerService webCrawlerService;
	
	@PostMapping
	public ResponseEntity<String> crawl(@RequestBody WebCrawlerRequestDto request) {
		log.debug("Crawl request received {}", request);
		String transactionId = webCrawlerService.crawl(request.getUrl(), request.getDepth());
		return ResponseEntity.ok(transactionId);
	}
	
	@GetMapping("/status")
	public ResponseEntity<CrawlStatus> status(@RequestParam ("transactionId") String transactionId) {
		log.debug("Fetch status for transaction id {}", transactionId);
		return ResponseEntity.ok(webCrawlerService.getStatus(transactionId));
	}
	
	@GetMapping("/result")
	public ResponseEntity<CrawlResult> fetchResult(@RequestParam ("transactionId") String transactionId) {
		log.debug("Fetch result for transaction id {}", transactionId);
		return ResponseEntity.ok(webCrawlerService.fetchResult(transactionId));
	}
}
