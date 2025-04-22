package com.crazyatom.domain.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.crazyatom.domain.dto.AllCategoryStatResponseDTO;
import com.crazyatom.domain.dto.BrandDTO;
import com.crazyatom.domain.dto.BrandStatResponseDTO;
import com.crazyatom.domain.dto.CategoryStatResponseDTO;
import com.crazyatom.domain.dto.ProductDTO;
import com.crazyatom.domain.entity.Brand;
import com.crazyatom.domain.entity.Product;
import com.crazyatom.domain.enums.ItemCategory;
import com.crazyatom.domain.enums.StatType;
import com.crazyatom.domain.exception.NotFoundException;
import com.crazyatom.domain.repository.BrandRepository;
import com.crazyatom.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceStatLoadService {

	private final ProductRepository productRepository;
	private final BrandRepository brandRepository;

	/**
	 * 특정 카테고리 내에서 최저가/최고가 아이템을 찾아 정보를 제공
	 * @param itemCategory 카테고리 (ex: 아우터, 팬츠, 스니커즈)
	 * @return 카테고리 통계 응답 dto
	 */
	public CategoryStatResponseDTO findCategoryPriceStat(ItemCategory itemCategory) {
		return CategoryStatResponseDTO.builder()
			.category(itemCategory.name())
			.minPriceProducts(findCategoryMinPriceProducts(itemCategory))
			.maxPriceProducts(findCategoryMaxPriceProducts(itemCategory))
			.build();
	}

	/**
	 * 특정 카테고리 내에서 최저가 아이템을 찾아 정보를 제공
	 * @param itemCategory 카테고리 (ex: 아우터, 팬츠, 스니커즈)
	 * @return 최저가 상품 dto 리스트
	 */
	private List<ProductDTO> findCategoryMinPriceProducts(ItemCategory itemCategory) {
		List<Product> products = productRepository.findFirst5ByCategoryAndUseTrueOrderByPriceAsc(
			itemCategory);

		if (products.isEmpty()) {
			throw new NotFoundException("해당 카테고리의 최저가 상품이 없습니다.");
		}

		long minPrice = products.stream().mapToLong(Product::getPrice).min().getAsLong();

		List<Product> minPriceProducts = products.stream()
			.filter(item -> item.getPrice() == minPrice)
			.toList();

		return minPriceProducts.stream()
			.map(x -> ProductDTO.builder()
				.brandName(x.getBrand().getBrandName())
				.category(x.getCategory().name())
				.price(x.getPrice())
				.build())
			.toList();
	}

	/**
	 * 특정 카테고리 내에서 최고가 아이템을 찾아 정보를 제공
	 * @param itemCategory 카테고리 (ex: 아우터, 팬츠, 스니커즈)
	 * @return 최고가 상품 dto 리스트
	 */
	private List<ProductDTO> findCategoryMaxPriceProducts(ItemCategory itemCategory) {
		List<Product> products = productRepository.findFirst5ByCategoryAndUseTrueOrderByPriceDesc(
			itemCategory);

		if (products.isEmpty()) {
			throw new NotFoundException("해당 카테고리의 최저가 상품이 없습니다.");
		}

		long maxPrice = products.stream().mapToLong(Product::getPrice).max().getAsLong();

		List<Product> minPriceProducts = products.stream()
			.filter(item -> item.getPrice() == maxPrice)
			.toList();

		return minPriceProducts.stream()
			.map(x -> ProductDTO.builder()
				.brandName(x.getBrand().getBrandName())
				.category(x.getCategory().name())
				.price(x.getPrice())
				.build())
			.toList();
	}

	/**
	 * 모든 카테고리에 대해 최저가 정보를 제공
	 * @return 모든 카테고리 통계 dto 리스트
	 */
	public AllCategoryStatResponseDTO findAllCategoryMinPriceStat() {
		List<ItemCategory> categories = Arrays.stream(ItemCategory.values()).toList(); // 모든 카테고리 정보를 리스트로 가져옴

		if (categories.isEmpty()) {
			throw new NotFoundException("카테고리가 존재하지 않습니다.");
		}

		return AllCategoryStatResponseDTO.builder()
			.statType(StatType.MIN_PRICE.name())
			.categoryStatList(
				categories.stream()
					.map(this::findCategoryMinPriceProducts) // 각 카테고리별 최저가 상품 목록을 조회
					.flatMap(List::stream) // 중첩된 리스트를 단일 스트림으로 평탄화
					.sorted(Comparator.comparing(ProductDTO::getCategory)) // 카테고리 기준으로 정렬
					.toList()
			)
			.build();
	}

	/**
	 * 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드명 및 브랜드 상품정보 제공
	 * @return 브랜드 통계 및 상품정보 dto
	 */
	public BrandStatResponseDTO findBrandPriceStat() {
		List<Brand> minPriceCandidates = brandRepository.findFirst5ByUseTrueOrderByTotalPriceAsc();

		if (minPriceCandidates.isEmpty()) {
			throw new NotFoundException("브랜드 데이터가 존재하지 않습니다.");
		}

		long minPrice = minPriceCandidates.stream().mapToLong(Brand::getTotalPrice).min().getAsLong();

		return BrandStatResponseDTO.builder()
			.statType(StatType.MIN_PRICE.name())
			.brandStatList(getMinPriceBrandDTOList(minPriceCandidates, minPrice))
			.build();
	}

	private List<BrandDTO> getMinPriceBrandDTOList(List<Brand> brands, long minPrice) {
		return brands.stream()
			.filter(brand -> brand.getTotalPrice() == minPrice)
			.map(this::convertToBrandDTO)
			.toList();
	}

	private BrandDTO convertToBrandDTO(Brand brand) {
		return BrandDTO.builder()
			.brandName(brand.getBrandName())
			.productList(convertToProductDTOList(brand.getProducts()))
			.totalPrice(brand.getTotalPrice())
			.build();
	}

	private List<ProductDTO> convertToProductDTOList(List<Product> products) {
		return products.stream()
			.map(product -> ProductDTO.builder()
				.brandName(product.getBrand().getBrandName())
				.category(product.getCategory().name())
				.price(product.getPrice())
				.build())
			.toList();
	}

}
