package com.tsystem.webcrawler.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CrawlResult {

	private Integer totalLinks;
	
	private Integer totalImages;
	
	private Set<Page> pages;
}
