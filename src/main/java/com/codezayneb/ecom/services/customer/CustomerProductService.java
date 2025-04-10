package com.codezayneb.ecom.services.customer;

import com.codezayneb.ecom.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {


    List<ProductDto> searchProductByTitle(String title);

    List<ProductDto> getAllProducts();
}
