INSERT INTO users (email, password, first_name, last_name, role) VALUES ('test@example.com', 'password', 'Test', 'User', 'USER');

-- Brands for watches and perfumes
INSERT INTO brands (name, description) VALUES 
('Sonata', 'Quality watches for everyone'),
('Titan', 'Premium watch manufacturer'),
('Fastrack', 'Trendy watches for youth'),
('Casio', 'Japanese electronics and watches'),
('Chanel', 'Luxury French fashion and perfume'),
('Dior', 'High-end French perfume house'),
('Versace', 'Italian luxury fashion and fragrance'),
('Gucci', 'Italian luxury brand'),
('Armani', 'Italian fashion and fragrance'),
('Calvin Klein', 'American fashion and fragrance brand');

-- Categories for both watches and perfumes
INSERT INTO categories (name) VALUES 
('Men'), 
('Women'), 
('Kids'), 
('Sport'), 
('Luxury'),
('Eau de Parfum'),
('Eau de Toilette'),
('Floral'),
('Oriental'),
('Fresh');

-- Watch products
INSERT INTO products (name, description, price, image_url, category_id, brand_id, stock_quantity) VALUES 
('Sonata Gold Analog', 'Classic gold analog watch for men, water resistant.', 2500.00, 'https://staticimg.titan.co.in/Sonata/Catalog/77083YM01_1.jpg', 1, 1, 100),
('Sonata Sleek Black', 'Modern black dial watch with leather strap.', 1800.00, 'https://staticimg.titan.co.in/Sonata/Catalog/7133KM01_1.jpg', 1, 1, 50),
('Sonata Rose Gold Women', 'Elegant rose gold watch for women.', 3200.00, 'https://staticimg.titan.co.in/Sonata/Catalog/8976WM01_1.jpg', 2, 1, 30),
('Titan Neo', 'Sophisticated chronograph watch.', 5500.00, 'https://staticimg.titan.co.in/Titan/Catalog/1733KM01_1.jpg', 1, 2, 20);

-- Perfume products
INSERT INTO products (name, description, price, image_url, category_id, brand_id, stock_quantity) VALUES 
('Chanel No. 5', 'Iconic floral-aldehyde fragrance for women. Timeless and sophisticated.', 12500.00, 'https://www.chanel.com/images/q_auto,f_auto/w_568/FSH-1459536/chanel-no-5-eau-de-parfum-spray-3-4-fl-oz.jpg', 6, 5, 50),
('Dior Sauvage', 'Fresh and spicy men\'s fragrance with notes of bergamot and pepper.', 9800.00, 'https://www.dior.com/beauty/version-5.1567765517105/resize-image/ep/1/3/0/3/0/tGC_13030073_F106810009_E01_GH.jpg', 7, 6, 60),
('Versace Eros', 'Passionate men\'s fragrance with mint, lemon, and vanilla notes.', 7500.00, 'https://www.versace.com/dw/image/v2/ABAO_PRD/on/demandware.static/-/Sites-ver-master-catalog/default/dw0d0a9e0e/original/90_1000882-1A00423_1A000_10_VersaceEros-Fragrances-versace-online-store.jpg', 7, 7, 45),
('Gucci Bloom', 'Floral women\'s fragrance with jasmine, tuberose, and rangoon creeper.', 10200.00, 'https://media.gucci.com/style/DarkGray_Center_0_0_490x490/1479140745/397815_99999_0099_001_100_0000_Light-Gucci-Bloom-Eau-de-Parfum-100ml.jpg', 8, 8, 40),
('Armani Code', 'Oriental men\'s fragrance with bergamot, lemon, and tonka bean.', 8500.00, 'https://www.armanibeauty.com/dw/image/v2/AAQR_PRD/on/demandware.static/-/Sites-armani-master-catalog/default/dw2f0e0e0e/images/LCODE/large/3614272049406.jpg', 9, 9, 55),
('Calvin Klein Eternity', 'Fresh floral women\'s fragrance, a timeless classic.', 6800.00, 'https://www.calvinklein.us/dw/image/v2/AAFP_PRD/on/demandware.static/-/Sites-ck-master-catalog/default/dw0d0a9e0e/images/large/25869_300.jpg', 10, 10, 70);
