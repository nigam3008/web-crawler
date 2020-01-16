package com.tsystem.webcrawler.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.tsystem.webcrawler.entity.UrlCrawlInfoEntity;

public interface UrlCrawlInfoRepo extends JpaRepository<UrlCrawlInfoEntity, Long> {

	List<UrlCrawlInfoEntity> findByTransactionId(@Param("transactionId") String transactionId);
}
