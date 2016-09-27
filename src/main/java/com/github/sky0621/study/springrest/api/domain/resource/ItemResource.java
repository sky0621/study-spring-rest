package com.github.sky0621.study.springrest.api.domain.resource;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ItemResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bookId;

	private String name;

	private List<String> authors;

	@JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate publishedDate;

	private BookPublisher publisher;

	public static class BookPublisher implements Serializable {

		private static final long serialVersionUID = 1L;

		private String name;

		private String tel;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

	}

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

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	public BookPublisher getPublisher() {
		return publisher;
	}

	public void setPublisher(BookPublisher publisher) {
		this.publisher = publisher;
	}

}
