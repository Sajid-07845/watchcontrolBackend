package com.watchstore.service.impl;

import com.watchstore.dto.ProductDTO;
import com.watchstore.dto.ProductRequest;
import com.watchstore.enums.ProductType;
import com.watchstore.exception.ResourceNotFoundException;
import com.watchstore.mapper.ProductMapper;
import com.watchstore.model.Brand;
import com.watchstore.model.Category;
import com.watchstore.model.Product;
import com.watchstore.repository.BrandRepository;
import com.watchstore.repository.CategoryRepository;
import com.watchstore.repository.ProductRepository;
import com.watchstore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    @CacheEvict(value = "products", allEntries = true)
    public ProductDTO createProduct(ProductRequest productRequest) {
        Product product = productMapper.toEntity(productRequest);
        
        // Resolve Brand and Category
        Brand brand = brandRepository.findByName(productRequest.getBrand())
            .orElseGet(() -> {
                Brand newBrand = new Brand();
                newBrand.setName(productRequest.getBrand());
                return brandRepository.save(newBrand);
            });
        
        Category category = categoryRepository.findByName(productRequest.getCategory())
            .orElseGet(() -> {
                Category newCategory = new Category();
                newCategory.setName(productRequest.getCategory());
                return categoryRepository.save(newCategory);
            });

        product.setBrand(brand);
        product.setCategory(category);
        product.setProductType(ProductType.valueOf(productRequest.getProductType().toUpperCase()));
        
        // Handle Price and Discount
        if (productRequest.getOriginalPrice() != null && productRequest.getDiscountPercentage() != null) {
            BigDecimal discount = productRequest.getOriginalPrice()
                .multiply(BigDecimal.valueOf(productRequest.getDiscountPercentage()))
                .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP);
            product.setPrice(productRequest.getOriginalPrice().subtract(discount));
            product.setOriginalPrice(productRequest.getOriginalPrice());
            product.setDiscountPercentage(productRequest.getDiscountPercentage());
        } else if (productRequest.getPrice() != null) {
            product.setPrice(productRequest.getPrice());
            product.setOriginalPrice(productRequest.getPrice());
            product.setDiscountPercentage(0);
        }

        Product savedProduct = productRepository.save(product);
        log.info("Product created and cache evicted for id: {}", savedProduct.getId());
        return productMapper.toDTO(savedProduct);
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductDTO getProductById(Long id) {
        log.info("Fetching product from DATABASE for id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toDTO(product);
    }

    @Override
    @Cacheable(value = "products", key = "'all-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        log.info("Fetching ALL products from DATABASE - Page: {}", pageable.getPageNumber());
        return productRepository.findAll(pageable)
                .map(productMapper::toDTO);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "products", key = "#id"),
        @CacheEvict(value = "products", allEntries = true)
    })
    public ProductDTO updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Update fields via Mapper or Manual
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity() != null ? productRequest.getStockQuantity() : product.getStockQuantity());
        
        // Handle Price and Discount Update
        if (productRequest.getOriginalPrice() != null && productRequest.getDiscountPercentage() != null) {
            BigDecimal discount = productRequest.getOriginalPrice()
                .multiply(BigDecimal.valueOf(productRequest.getDiscountPercentage()))
                .divide(BigDecimal.valueOf(100), 2, java.math.RoundingMode.HALF_UP);
            product.setPrice(productRequest.getOriginalPrice().subtract(discount));
            product.setOriginalPrice(productRequest.getOriginalPrice());
            product.setDiscountPercentage(productRequest.getDiscountPercentage());
        } else if (productRequest.getPrice() != null) {
            product.setPrice(productRequest.getPrice());
            product.setOriginalPrice(productRequest.getPrice());
            product.setDiscountPercentage(0);
        }

         // Resolve Brand and Category
        Brand brand = brandRepository.findByName(productRequest.getBrand())
            .orElseGet(() -> {
                Brand newBrand = new Brand();
                newBrand.setName(productRequest.getBrand());
                return brandRepository.save(newBrand);
            });
        
        Category category = categoryRepository.findByName(productRequest.getCategory())
            .orElseGet(() -> {
                Category newCategory = new Category();
                newCategory.setName(productRequest.getCategory());
                return categoryRepository.save(newCategory);
            });

        product.setBrand(brand);
        product.setCategory(category);
        product.setProductType(ProductType.valueOf(productRequest.getProductType().toUpperCase()));

        Product updatedProduct = productRepository.save(product);
        log.info("Product updated and cache evicted for id: {}", id);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value = "products", key = "#id"),
        @CacheEvict(value = "products", allEntries = true)
    })
    public void deleteProduct(Long id) {
        log.info("Deleting product and evicting cache for id: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    @Override
    @Cacheable(value = "products", key = "'cat-' + #categoryName + '-' + #pageable.pageNumber")
    public Page<ProductDTO> getProductsByCategory(String categoryName, Pageable pageable) {
        Category category = categoryRepository.findByName(categoryName)
             .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryName));

        return productRepository.findByCategory(category, pageable)
                .map(productMapper::toDTO);
    }

    @Override
    @Cacheable(value = "products", key = "'type-' + #type + '-' + #pageable.pageNumber")
    public Page<ProductDTO> getProductsByType(ProductType type, Pageable pageable) {
        return productRepository.findByProductType(type, pageable)
                .map(productMapper::toDTO);
    }

    @Override
    @Cacheable(value = "products", key = "'search-' + #searchTerm + '-' + #pageable.pageNumber")
    public Page<ProductDTO> searchProducts(ProductType type, String searchTerm, Pageable pageable) {
        return productRepository.searchByType(type, searchTerm, pageable)
                .map(productMapper::toDTO);
    }
}
