package com.crazyatom.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	@Schema(description = "상품 이름")
	public String brandName;
	@Schema(description = "상품 카테고리")
	public String category;
	@Schema(description = "상품 가격")
	public Long price;
}
