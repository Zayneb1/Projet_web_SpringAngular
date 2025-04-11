package com.codezayneb.ecom.services.admin.adminproduct;

import com.codezayneb.ecom.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {


    public ProductDto addProduct(ProductDto productDto) throws IOException;
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllProductsByName(String name);
    boolean deleteProduct(Long id);
    ProductDto getProductById(Long productId);
    ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;
}
