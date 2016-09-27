package com.github.sky0621.study.springrest.api.domain.handler;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.sky0621.study.springrest.api.domain.exception.ApiError;
import com.github.sky0621.study.springrest.api.domain.exception.ItemResourceNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private final Map<Class<? extends Exception>, String> messageMappings = Collections
			.unmodifiableMap(new LinkedHashMap() {
				{
					put(HttpMessageNotReadableException.class, "Request body is invalid");
					put(MethodArgumentNotValidException.class, "Request value is invalid");
				}
			});

	@Autowired
	MessageSource messageSource;

	private String resolveMessage(Exception ex, String defaultMessage) {
		return messageMappings.entrySet().stream().filter(entry -> entry.getKey().isAssignableFrom(ex.getClass()))
				.findFirst().map(Map.Entry::getValue).orElse(defaultMessage);
	}

	private ApiError createApiError(Exception ex, String documentationUrl) {
		return createApiError(ex, documentationUrl, ex.getMessage());
	}

	private ApiError createApiError(Exception ex, String documentationUrl, String defaultMessage) {
		ApiError apiError = new ApiError();
		apiError.setMessage(resolveMessage(ex, defaultMessage));
		apiError.setDocumentationUrl(documentationUrl);
		return apiError;
	}

	// フレームワークが返す例外をハンドリング！
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError apiError = createApiError(ex, "http://localhost:8080/api/errors");
		return super.handleExceptionInternal(ex, apiError, headers, status, request);
	}

	// アプリ固有の例外をハンドリング！
	@ExceptionHandler
	public ResponseEntity<Object> handleItemResourceNotFoundException(ItemResourceNotFoundException ex,
			WebRequest request) {
		return super.handleExceptionInternal(ex, null, null, HttpStatus.NOT_FOUND, request);
	}

	// システム例外をハンドリング！
	@ExceptionHandler
	public ResponseEntity<Object> handleSystemException(Exception ex, WebRequest request) {
		ApiError apiError = createApiError(ex, "http://localhost:8080/api/errors", "System error is occurred");
		return super.handleExceptionInternal(ex, apiError, null, HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	// パラメータチェック例外をハンドリング！
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = createApiError(ex, ex.getMessage());

		// まずグローバルエラーをディテイルに詰める！
		ex.getBindingResult().getGlobalErrors().stream()
				.forEach(e -> apiError.addDetail(e.getObjectName(), getMessage(e, request)));

		// 次はフィールドエラーをディテイルに詰める！
		ex.getBindingResult().getFieldErrors().stream()
				.forEach(e -> apiError.addDetail(e.getObjectName(), getMessage(e, request)));

		return super.handleExceptionInternal(ex, apiError, headers, status, request);
	}

	private String getMessage(MessageSourceResolvable resolvable, WebRequest request) {
		return messageSource.getMessage(resolvable, request.getLocale());
	}

}
