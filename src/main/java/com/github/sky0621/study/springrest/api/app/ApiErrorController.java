package com.github.sky0621.study.springrest.api.app;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.sky0621.study.springrest.api.domain.exception.ApiError;

@RestController
@RequestMapping("error")
public class ApiErrorController {

	@RequestMapping(method = RequestMethod.GET)
	public ApiError handleError(HttpServletRequest request) {
		String message;
		Exception ex = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		Integer statusCode = (Integer) request.getAttribute(ERROR_STATUS_CODE);
		if (ex != null) {
			message = ex.getMessage();
		} else {
			if (Arrays.asList(HttpStatus.values()).stream().anyMatch(status -> status.value() == statusCode)) {
				message = HttpStatus.valueOf(statusCode).getReasonPhrase();
			} else {
				message = "Custom error(" + statusCode + ") is occurred";
			}
		}
		ApiError apiError = new ApiError();
		apiError.setMessage(message);
		apiError.setDocumentationUrl("http://localhost:8080/api/errors2");
		return apiError;
	}

}
