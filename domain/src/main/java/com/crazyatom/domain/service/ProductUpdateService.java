package com.crazyatom.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crazyatom.domain.dto.BrandDTO;
import com.crazyatom.domain.dto.ProductDTO;
import com.crazyatom.domain.entity.Brand;
import com.crazyatom.domain.entity.Product;
import com.crazyatom.domain.enums.ItemCategory;
import com.crazyatom.domain.exception.BusinessException;
import com.crazyatom.domain.exception.ErrorCode;
import com.crazyatom.domain.exception.NotFoundException;
import com.crazyatom.domain.exception.ValidationException;
import com.crazyatom.domain.repository.BrandRepository;
import com.crazyatom.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductUpdateService {

	private final ProductRepository productRepository;
	private final BrandRepository brandRepository;

	public void createBrand(BrandDTO dto) {
		String brandName = dto.getBrandName();

		boolean exists = brandRepository.findByBrandNameAndUseTrue(brandName).isPresent();
		if (exists) {
			throw new BusinessException(ErrorCode.DUPLICATE_ERROR, "이미 존재하는 브랜드입니다.");
		}

		Brand newBrand = Brand.createBrand(brandName, dto.getProductList());

		List<Product> products = dto.getProductList()
			.stream()
			.map(x -> {
				Product p = new Product();
				p.setBrand(newBrand);
				try {
					p.setCategory(ItemCategory.valueOf(x.getCategory()));
				} catch (Exception e) {
					throw new ValidationException("유효하지 않은 카테고리입니다: " + x.getCategory());
				}
				p.setPrice(x.getPrice());
				p.setUse(true);
				return p;
			}).toList();

		brandRepository.save(newBrand);
		productRepository.saveAll(products);
	}

	@Retryable(
		value = {OptimisticLockingFailureException.class},
		maxAttempts = 2
	)
	public void updateProductPrice(ProductDTO dto) {
		Optional<Brand> brandOptional = brandRepository.findByBrandNameAndUseTrue(dto.getBrandName());

		Brand brand = brandRepository.findByBrandNameAndUseTrue(dto.getBrandName())
			.orElseThrow(() -> new NotFoundException("브랜드를 찾을 수 없습니다."));

		ItemCategory category = Optional.ofNullable(dto.getCategory())
			.map(ItemCategory::valueOf)
			.orElseThrow(() -> new ValidationException("유효하지 않은 카테고리입니다: " + dto.getCategory()));

		Product product = brand.getProducts()
			.stream()
			.filter(x -> x.getCategory() == category)
			.findFirst()
			.orElseThrow(() -> new NotFoundException("해당 카테고리의 상품을 찾을 수 없습니다."));

		long beforePrice = product.getPrice();
		long newPrice = dto.getPrice();

		product.setPrice(newPrice);
		brand.setTotalPrice(brand.getTotalPrice() - beforePrice + newPrice);
	}

	@Retryable(
		value = {OptimisticLockingFailureException.class},
		maxAttempts = 2
	)
	public void deleteProducts(String brandName) {
		Optional<Brand> brandOptional = brandRepository.findByBrandNameAndUseTrue(brandName);

		Brand brand = brandRepository.findByBrandNameAndUseTrue(brandName)
			.orElseThrow(() -> new NotFoundException("브랜드를 찾을 수 없습니다."));

		brand.setUse(false);
		brand.getProducts().forEach(x -> x.setUse(false));
	}

}
