package com.github.sky0621.study.springrest.domain.resource;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ItemResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bookId;

	private String name;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate publishedDate;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

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
