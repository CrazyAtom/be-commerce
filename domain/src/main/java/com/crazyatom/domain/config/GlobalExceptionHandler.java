package com.crazyatom.domain.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crazyatom.common.dto.ApiResponse;
import com.crazyatom.common.dto.ErrorResponse;
import com.crazyatom.domain.exception.BusinessException;
import com.crazyatom.domain.exception.ErrorCode;
import com.crazyatom.domain.exception.NotFoundException;
import com.crazyatom.domain.exception.ValidationException;

/**
 * 애플리케이션의 전역 예외 처리를 담당하는 클래스입니다.
 * 모든 컨트롤러에서 발생하는 예외를 일관된 형식으로 처리합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 비즈니스 로직 실행 중 발생하는 예외를 처리합니다.
	 * @param e 비즈니스 예외
	 * @return 에러 응답 객체
	 */
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
		ErrorResponse error = new ErrorResponse(e.getErrorCode().name(), e.getMessage());
		return new ResponseEntity<>(ApiResponse.error(error), e.getHttpStatus());
	}

	/**
	 * 입력값 검증 실패 시 발생하는 예외를 처리합니다.
	 * @param e 검증 예외
	 * @return 에러 응답 객체
	 */
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ApiResponse<?>> handleValidationException(ValidationException e) {
		ErrorResponse error = new ErrorResponse(e.getErrorCode().name(), e.getMessage());
		return new ResponseEntity<>(ApiResponse.error(error), e.getHttpStatus());
	}

	/**
	 * 요청한 리소스를 찾을 수 없을 때 발생하는 예외를 처리합니다.
	 * @param e Not Found 예외
	 * @return 에러 응답 객체
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handleNotFoundException(NotFoundException e) {
		ErrorResponse error = new ErrorResponse(e.getErrorCode().name(), e.getMessage());
		return new ResponseEntity<>(ApiResponse.error(error), e.getHttpStatus());
	}

	/**
	 * Spring의 요청 파라미터 바인딩 및 검증 실패 시 발생하는 예외를 처리합니다.
	 * @param e Method Argument Not Valid 예외
	 * @return 에러 응답 객체
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		ErrorResponse error = new ErrorResponse(ErrorCode.VALIDATION_ERROR.name(), defaultMessage);
		return ResponseEntity.badRequest().body(ApiResponse.error(error));
	}

	/**
	 * 기타 예상하지 못한 예외를 처리합니다.
	 * @param e 예외
	 * @return 에러 응답 객체
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
		ErrorResponse error = new ErrorResponse("INTERNAL_SERVER_ERROR", e.getMessage());
		return ResponseEntity.internalServerError().body(ApiResponse.error(error));
	}
}
