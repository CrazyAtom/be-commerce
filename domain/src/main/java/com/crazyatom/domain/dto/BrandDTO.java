package com.crazyatom.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
	public String brandName;
	public List<ProductDTO> productList;
	public Long totalPrice;
}
