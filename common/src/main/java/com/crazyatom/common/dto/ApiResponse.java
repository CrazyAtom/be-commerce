package com.crazyatom.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "API 응답 객체")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

	@Schema(description = "성공 여부")
	private final boolean success;
	@Schema(description = "성공 시 응답 데이터")
	private final T data;
	@Schema(description = "실패 시 에러 정보")
	private final ErrorResponse error;

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, data, null);
	}

	public static ApiResponse<?> error(ErrorResponse error) {
		return new ApiResponse<>(false, null, error);
	}
}
