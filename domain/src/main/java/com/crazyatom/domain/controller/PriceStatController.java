package com.crazyatom.domain.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crazyatom.common.dto.ApiResponse;
import com.crazyatom.domain.dto.AllCategoryStatResponseDTO;
import com.crazyatom.domain.dto.BrandStatResponseDTO;
import com.crazyatom.domain.dto.CategoryStatResponseDTO;
import com.crazyatom.domain.enums.ItemCategory;
import com.crazyatom.domain.exception.ValidationException;
import com.crazyatom.domain.service.PriceStatLoadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "상품 가격 통계", description = "상품 가격 통계 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stats")
public class PriceStatController {

	private final PriceStatLoadService priceStatLoadService;

	@Operation(summary = "카테고리 통계", description = "특정 카테고리의 최저가/최고가 상품 통계를 조회합니다.")
	@GetMapping("/category")
	public ApiResponse<CategoryStatResponseDTO> getCategoryStat(
		@Parameter(description = "카테고리 이름")
		@RequestParam("name") String category) {
		Set<String> validCategoryNames = ItemCategory.getNames();
		if (!validCategoryNames.contains(category)) {
			throw new ValidationException("카테고리 값이 틀립니다. 정확한 카테고리명을 입력하세요.");
		}

		CategoryStatResponseDTO result = priceStatLoadService.findCategoryPriceStat(ItemCategory.valueOf(category));
		return ApiResponse.success(result);
	}

	@Operation(summary = "전체 카테고리 통계", description = "모든 카테고리의 최저가 상품 통계를 조회합니다.")
	@GetMapping("/category/all")
	public ApiResponse<AllCategoryStatResponseDTO> getAllCategoryStat() {
		AllCategoryStatResponseDTO result = priceStatLoadService.findAllCategoryMinPriceStat();
		return ApiResponse.success(result);
	}

	@Operation(summary = "브랜드 통계", description = "브랜드별 상품 가격 통계를 조회합니다.")
	@GetMapping("/brand")
	public ApiResponse<BrandStatResponseDTO> getBrandStat() {
		BrandStatResponseDTO result = priceStatLoadService.findBrandPriceStat();
		return ApiResponse.success(result);
	}
}
