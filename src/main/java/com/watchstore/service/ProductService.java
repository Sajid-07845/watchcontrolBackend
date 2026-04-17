package com.watchstore.service;

import com.watchstore.dto.ProductDTO;
import com.watchstore.dto.ProductRequest;

import com.watchstore.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductRequest productRequest);
    ProductDTO getProductById(Long id);
    Page<ProductDTO> getAllProducts(Pageable pageable);
    ProductDTO updateProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);
    Page<ProductDTO> getProductsByCategory(String category, Pageable pageable);
    Page<ProductDTO> getProductsByType(ProductType type, Pageable pageable);
    Page<ProductDTO> searchProducts(ProductType type, String searchTerm, Pageable pageable);
}
