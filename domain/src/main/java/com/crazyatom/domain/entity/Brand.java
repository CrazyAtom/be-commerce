package com.crazyatom.domain.entity;

import java.util.List;

import com.crazyatom.domain.dto.ProductDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Brand {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "brand_id")
	private Long id;

	@Column(nullable = false)
	private String brandName;

	private Long totalPrice;

	@Column(nullable = false, columnDefinition = "boolean default true")
	private boolean use;

	@Version
	private int version;

	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Product> products;

	public static Brand createBrand(String brandName, List<ProductDTO> products) {
		Brand brand = new Brand();
		brand.setBrandName(brandName);
		brand.setTotalPrice(products.stream().mapToLong(ProductDTO::getPrice).sum());
		brand.setUse(true);
		return brand;
	}
}
