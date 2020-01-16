package com.tsystem.webcrawler.dto;

public class WebCrawlerRequestDto extends BaseDto {

	private String url;

	private Integer depth;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}
}
