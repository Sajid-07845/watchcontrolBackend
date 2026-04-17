package com.watchstore.repository;

import com.watchstore.model.Brand;
import com.watchstore.model.Category;
import com.watchstore.model.Product;
import com.watchstore.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory(Category category, Pageable pageable);
    Page<Product> findByBrand(Brand brand, Pageable pageable);
    Page<Product> findByProductType(ProductType productType, Pageable pageable);

    @Query("SELECT p FROM Product p JOIN p.brand b WHERE p.productType = :type AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(b.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Product> searchByType(@Param("type") ProductType type, @Param("searchTerm") String searchTerm, Pageable pageable);
}
