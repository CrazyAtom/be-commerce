package com.crazyatom.common.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

	private final boolean success;        // 성공 여부
	private final T data;                // 성공 시 데이터
	private final ErrorResponse error;    // 실패 시 에러 정보

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, data, null);
	}

	public static ApiResponse<?> error(ErrorResponse error) {
		return new ApiResponse<>(false, null, error);
	}
}
