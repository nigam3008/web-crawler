package com.tsystem.webcrawler.dto;

import org.jsoup.select.Elements;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageDetail {

	private String url;

	private int imageCount;

	private Elements links;
	
	private String title;
}
