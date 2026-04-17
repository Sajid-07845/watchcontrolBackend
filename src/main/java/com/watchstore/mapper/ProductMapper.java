package com.watchstore.mapper;

import com.watchstore.dto.ProductDTO;
import com.watchstore.dto.ProductRequest;
import com.watchstore.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = modelMapper.map(product, ProductDTO.class);
        // Manually map Brand and Category names because DTO expects String but Entity has Object
        if (product.getBrand() != null) {
            dto.setBrand(product.getBrand().getName());
        }
        if (product.getCategory() != null) {
            dto.setCategory(product.getCategory().getName());
        }
        return dto;
    }

    public Product toEntity(ProductRequest productRequest) {
        // We only map fields that scalar. Relations (Brand/Category) are handled in Service
        return modelMapper.map(productRequest, Product.class);
    }

    public void updateEntityFromRequest(ProductRequest request, Product product) {
        modelMapper.map(request, product);
    }
}
