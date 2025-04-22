package com.crazyatom.domain.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
	private final ErrorCode errorCode;

	public ValidationException(String message) {
		super(message);
		this.errorCode = ErrorCode.VALIDATION_ERROR;
	}

	public ValidationException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
