package com.watchstore.config;

import com.watchstore.enums.WatchBrand;
import com.watchstore.enums.WatchCategory;
import com.watchstore.enums.Role;
import com.watchstore.enums.PerfumeCategory;
import com.watchstore.enums.PerfumeBrand;
import com.watchstore.model.User;
import java.util.Optional;
import com.watchstore.model.Brand;
import com.watchstore.model.Category;
import com.watchstore.repository.BrandRepository;
import com.watchstore.repository.CategoryRepository;
import com.watchstore.repository.UserRepository;
import com.watchstore.repository.ProductRepository;
import com.watchstore.enums.ProductType;
import com.watchstore.model.Product;
import java.util.List;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@watchstore.com").isEmpty()) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@watchstore.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("Default Admin user created: admin@watchstore.com / admin123");
        }

        // Seed Categories from Enum
        for (WatchCategory cat : WatchCategory.values()) {
            seedCategory(cat.getDisplayName());
        }

        // Seed Watch Brands from Enum
        for (WatchBrand brand : WatchBrand.values()) {
            seedBrand(brand.getDisplayName(), "Premium Watchmaker");
        }

        // Seed Perfume Categories from Enum
        for (PerfumeCategory cat : PerfumeCategory.values()) {
            seedCategory(cat.getDisplayName());
        }

        // Seed Perfume Brands from Enum
        for (PerfumeBrand brand : PerfumeBrand.values()) {
            seedBrand(brand.getDisplayName(), "Luxury Fragrance");
        }

        migrateExistingProducts();
    }

    private void migrateExistingProducts() {
        List<Product> productsToMigrate = productRepository.findAll().stream()
                .filter(p -> p.getProductType() == null)
                .toList();

        if (!productsToMigrate.isEmpty()) {
            System.out.println("Migrating " + productsToMigrate.size() + " products to new categorization...");
            
            List<String> watchCategories = Arrays.stream(WatchCategory.values())
                    .map(WatchCategory::getDisplayName)
                    .toList();

            List<String> perfumeCategories = Arrays.stream(PerfumeCategory.values())
                    .map(PerfumeCategory::getDisplayName)
                    .toList();

            for (Product product : productsToMigrate) {
                // Only proceed if category is not null, otherwise we can't determine type
                if (product.getCategory() != null) {
                    // Determine type based on category if productType is null
                    if (product.getProductType() == null) {
                        String categoryName = product.getCategory().getName();
                        if (watchCategories.contains(categoryName)) {
                            product.setProductType(ProductType.WATCH);
                        } else if (perfumeCategories.contains(categoryName)) {
                            product.setProductType(ProductType.PERFUME);
                        } else {
                            // Default to WATCH if unknown for this store
                            product.setProductType(ProductType.WATCH);
                        }
                    }
                    
                    // Initialize discount fields if originalPrice is null
                    if (product.getOriginalPrice() == null) {
                        product.setOriginalPrice(product.getPrice());
                        product.setDiscountPercentage(0);
                    }
                    
                    productRepository.save(product);
                }
            }
            System.out.println("Migration complete.");
        }
    }

    private void seedCategory(String name) {
        if (categoryRepository.findByName(name).isEmpty()) {
            categoryRepository.save(new Category(null, name));
            System.out.println("Seeded category: " + name);
        }
    }

    private void seedBrand(String name, String description) {
        if (brandRepository.findByName(name).isEmpty()) {
            brandRepository.save(new Brand(null, name, description));
            System.out.println("Seeded brand: " + name);
        }
    }
}
