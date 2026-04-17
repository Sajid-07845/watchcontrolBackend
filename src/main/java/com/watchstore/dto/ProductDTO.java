package com.watchstore.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer discountPercentage;
    private String imageUrl;
    private String category;
    private String brand;
    private String productType;
}
