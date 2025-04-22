package com.crazyatom.domain.entity;

import com.crazyatom.domain.enums.ItemCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;

	// Enum 값을 문자열로 저장하기 위해 @Enumerated(EnumType.STRING) 사용
	@Enumerated(EnumType.STRING)
	private ItemCategory category;

	private long price;

	@ManyToOne
	// @JoinColumn(name = "brand_id", referencedColumnName = "id", nullable = false)
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;

	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean use;

	@Version
	private int version;
}
