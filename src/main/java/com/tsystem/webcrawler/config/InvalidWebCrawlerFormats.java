package com.tsystem.webcrawler.config;

public enum InvalidWebCrawlerFormats {
	
	SWF(".swf"),
	PDF(".pdf"),
	PNG(".png"),
	GIF(".gif"),
	JPG(".jpg"),
	JPEG(".jpeg");
	
	String value;
	
	InvalidWebCrawlerFormats(String value) {
		this.value = value;
	}
}
