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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stats")
public class PriceStatController {

	private final PriceStatLoadService priceStatLoadService;

	@GetMapping("/category")
	public ApiResponse<CategoryStatResponseDTO> getCategoryStat(@RequestParam("name") String category) {
		Set<String> validCategoryNames = ItemCategory.getNames();
		if (!validCategoryNames.contains(category)) {
			throw new ValidationException("카테고리 값이 틀립니다. 정확한 카테고리명을 입력하세요.");
		}

		CategoryStatResponseDTO result = priceStatLoadService.findCategoryPriceStat(ItemCategory.valueOf(category));
		return ApiResponse.success(result);
	}

	@GetMapping("/category/all")
	public ApiResponse<AllCategoryStatResponseDTO> getAllCategoryStat() {
		AllCategoryStatResponseDTO result = priceStatLoadService.findAllCategoryMinPriceStat();
		return ApiResponse.success(result);
	}

	@GetMapping("/brand")
	public ApiResponse<BrandStatResponseDTO> getBrandStat() {
		BrandStatResponseDTO result = priceStatLoadService.findBrandPriceStat();
		return ApiResponse.success(result);
	}
}
