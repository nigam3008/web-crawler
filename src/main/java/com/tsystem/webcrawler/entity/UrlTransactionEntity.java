package com.tsystem.webcrawler.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.tsystem.webcrawler.dto.CrawlStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "url_crawl_transaction")
@NoArgsConstructor
@Data
public class UrlTransactionEntity extends BaseEntity {

	private static final long serialVersionUID = 7031089121274475595L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String url;

	private String transactionId;

	@Enumerated(EnumType.STRING)
	private CrawlStatus status;
	
	private Integer depth;

	public UrlTransactionEntity(String url, String transactionId, CrawlStatus status, Integer depth) {
		super();
		this.url = url;
		this.transactionId = transactionId;
		this.status = status;
		this.depth = depth;
	}
}