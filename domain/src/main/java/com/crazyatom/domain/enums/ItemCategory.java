package com.crazyatom.domain.enums;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum ItemCategory {
	TOPS,
	OUTERWEAR,
	PANTS,
	SNEAKERS,
	BAG,
	HAT,
	SOCKS,
	ACCESSORIES;

	public static Set<String> getNames() {
		return Arrays.stream(ItemCategory.values())
			.map(Enum::name)
			.collect(Collectors.toSet());
	}
}
