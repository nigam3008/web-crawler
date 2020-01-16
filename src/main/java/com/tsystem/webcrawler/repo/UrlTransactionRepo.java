package com.tsystem.webcrawler.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsystem.webcrawler.entity.UrlTransactionEntity;

@Repository
public interface UrlTransactionRepo extends JpaRepository<UrlTransactionEntity, Long> {

	UrlTransactionEntity findByTransactionId(String transactionId);
}
