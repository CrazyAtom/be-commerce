package com.crazyatom.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crazyatom.domain.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
	Optional<Brand> findByBrandNameAndUseTrue(String brandName);

	List<Brand> findFirst5ByUseTrueOrderByTotalPriceAsc();
}
