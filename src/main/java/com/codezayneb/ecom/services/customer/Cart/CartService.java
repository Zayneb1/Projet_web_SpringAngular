package com.codezayneb.ecom.services.customer.Cart;

import com.codezayneb.ecom.dto.AddProductInCartDto;
import com.codezayneb.ecom.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {


    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
    OrderDto getCartByUserId (Long userId);
}
