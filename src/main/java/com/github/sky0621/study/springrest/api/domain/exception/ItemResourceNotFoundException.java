package com.github.sky0621.study.springrest.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ItemResourceNotFoundException(String itemId) {
		super("Item is not founc (itemId = " + itemId + ")");
	}

}
