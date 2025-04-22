package com.crazyatom.domain.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

	private final ErrorCode errorCode;

	public NotFoundException(String message) {
		super(message);
		this.errorCode = ErrorCode.NOT_FOUND_ERROR;
	}

	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
