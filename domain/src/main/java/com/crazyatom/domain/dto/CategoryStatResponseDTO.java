package com.crazyatom.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CategoryStatResponseDTO {
	public String category;
	public List<ProductDTO> minPriceProducts;
	public List<ProductDTO> maxPriceProducts;
}
