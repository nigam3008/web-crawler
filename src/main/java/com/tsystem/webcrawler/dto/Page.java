package com.tsystem.webcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Page {

	private String title;
	
	private String pageLink;
	
	private Integer imageCount;
}
