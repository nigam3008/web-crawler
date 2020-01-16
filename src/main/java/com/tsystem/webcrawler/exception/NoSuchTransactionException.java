package com.tsystem.webcrawler.exception;

public class NoSuchTransactionException extends RuntimeException {

	public NoSuchTransactionException(String transactionId) {
		super(transactionId);
	}

	private static final long serialVersionUID = -1479726251861956338L;

}
