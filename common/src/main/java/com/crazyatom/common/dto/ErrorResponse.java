package com.crazyatom.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	private String code;    // 에러코드 (ex: NOT_FOUND, VALIDATION_ERROR)
	private String message; // 에러 상세 메시지
}
