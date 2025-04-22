package com.crazyatom.domain.controller;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.crazyatom.common.dto.ApiResponse;
import com.crazyatom.domain.dto.BrandDTO;
import com.crazyatom.domain.dto.ProductDTO;
import com.crazyatom.domain.enums.ItemCategory;
import com.crazyatom.domain.exception.ValidationException;
import com.crazyatom.domain.service.ProductUpdateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "상품 관리", description = "상품 가격 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

	private final ProductUpdateService productUpdateService;

	@Operation(summary = "브랜드 등록", description = "새로운 브랜드와 상품들을 등록합니다.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<String> createBrand(
		@Parameter(description = "브랜드 등록 정보")
		@RequestBody @Valid BrandDTO brand) {
		Set<String> requestCategories = brand.getProductList()
			.stream()
			.map(ProductDTO::getCategory)
			.collect(Collectors.toSet());

		if (!requestCategories.equals(ItemCategory.getNames())) {
			throw new ValidationException("Category 명이 일치하지 않거나 포함되어 있지 않습니다.");
		}

		productUpdateService.createBrand(brand);
		return ApiResponse.success("success");
	}

	@Operation(summary = "상품 가격 수정", description = "특정 브랜드의 상품 가격을 수정합니다.")
	@PatchMapping
	public ApiResponse<String> updateProduct(
		@Parameter(description = "상품 수정 정보")
		@RequestBody @Valid ProductDTO product) {
		if (product.getBrandName() == null || product.getBrandName().trim().isEmpty()) {
			throw new ValidationException("브랜드명이 누락되었습니다.");
		}

		if (!ItemCategory.getNames().contains(product.getCategory())) {
			throw new ValidationException("사용되지 않는 카테고리입니다.");
		}

		productUpdateService.updateProductPrice(product);
		return ApiResponse.success("success");
	}

	@Operation(summary = "브랜드 삭제", description = "브랜드명으로 브랜드와 관련 상품을 삭제합니다.")
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProduct(
		@Parameter(description = "삭제할 브랜드명")
		@RequestParam("name") String brandName) {
		productUpdateService.deleteProducts(brandName);
	}
}
