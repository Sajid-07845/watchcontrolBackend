package com.watchstore.service;

import com.watchstore.model.Brand;
import java.util.List;

public interface BrandService {
    Brand createBrand(Brand brand);
    List<Brand> getAllBrands();
    Brand getBrandById(Long id);
}
