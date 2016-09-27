package com.github.sky0621.study.springrest.domain.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.sky0621.study.springrest.domain.exception.ApiError;
import com.github.sky0621.study.springrest.domain.exception.ItemResourceNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	// デフォルトではエラーのボディが空のため、メッセージを詰める。
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ApiError apiError = new ApiError();
		apiError.setMessage(ex.getMessage());
		apiError.setDocumentationUrl("http://localhost:8080/api/errors");
		return super.handleExceptionInternal(ex, apiError, headers, status, request);
	}

	// アプリ固有の例外をハンドリング！
	@ExceptionHandler
	public ResponseEntity<Object> handleItemResourceNotFoundException(ItemResourceNotFoundException ex,
			WebRequest request) {
		return super.handleExceptionInternal(ex, null, null, HttpStatus.NOT_FOUND, request);
	}

}
