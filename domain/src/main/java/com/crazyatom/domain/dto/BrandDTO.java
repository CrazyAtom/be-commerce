package com.crazyatom.domain.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
	@Schema(description = "브랜드 이름")
	public String brandName;
	@Schema(description = "상품 목록")
	public List<ProductDTO> productList;
	@Schema(description = "상품 전체 가격")
	public Long totalPrice;
}
