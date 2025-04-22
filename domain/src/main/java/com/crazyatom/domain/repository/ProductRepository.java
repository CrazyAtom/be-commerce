package com.crazyatom.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crazyatom.domain.entity.Product;
import com.crazyatom.domain.enums.ItemCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findFirst5ByCategoryAndUseTrueOrderByPriceDesc(ItemCategory category);

	List<Product> findFirst5ByCategoryAndUseTrueOrderByPriceAsc(ItemCategory category);
}
