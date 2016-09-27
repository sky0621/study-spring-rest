package com.github.sky0621.study.springrest.domain.criteria;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class ItemCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate publishedDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

}
