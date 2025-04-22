package com.crazyatom.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class BrandStatResponseDTO {
	public String statType;
	public List<BrandDTO> brandStatList;
}
