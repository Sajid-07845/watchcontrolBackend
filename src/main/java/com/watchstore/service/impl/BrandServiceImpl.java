package com.watchstore.service.impl;

import com.watchstore.exception.ResourceNotFoundException;
import com.watchstore.model.Brand;
import com.watchstore.repository.BrandRepository;
import com.watchstore.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired private BrandRepository brandRepository;

    @Override public Brand createBrand(Brand brand) { return brandRepository.save(brand); }
    @Override public List<Brand> getAllBrands() { return brandRepository.findAll(); }
    @Override public Brand getBrandById(Long id) { 
        return brandRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Brand not found")); 
    }
}
