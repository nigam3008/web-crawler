package com.tsystem.webcrawler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "url_crawl_info")
@NoArgsConstructor
@Data
public class UrlCrawlInfoEntity extends BaseEntity {

	private static final long serialVersionUID = 8734893284585358685L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String url;
	
	private Integer imageCount;
	
	private String pageTitle;
	
	private String transactionId;

	public UrlCrawlInfoEntity(String url, Integer imageCount, String pageTitle, String transactionId) {
		super();
		this.url = url;
		this.imageCount = imageCount;
		this.pageTitle = pageTitle;
		this.transactionId = transactionId;
	}
}
