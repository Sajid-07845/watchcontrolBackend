package com.watchstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    
    @NotBlank(message = "Product name is required")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String name;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer discountPercentage;
    private Integer stockQuantity;

    @NotBlank(message = "Image URL is required")
    @Size(max = 2000, message = "Image URL must be less than 2000 characters")
    private String imageUrl;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Product type is required")
    private String productType;
}
