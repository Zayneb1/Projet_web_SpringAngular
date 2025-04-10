package com.codezayneb.ecom.services.customer.Cart;

import com.codezayneb.ecom.dto.AddProductInCartDto;
import com.codezayneb.ecom.dto.CartItemsDto;
import com.codezayneb.ecom.dto.OrderDto;
import com.codezayneb.ecom.entity.CartItems;
import com.codezayneb.ecom.entity.Product;
import com.codezayneb.ecom.entity.User;
import com.codezayneb.ecom.enums.OrderStatus;
import com.codezayneb.ecom.repository.CartItemsRepository;
import com.codezayneb.ecom.repository.OrderRepository;
import com.codezayneb.ecom.entity.Order;
import com.codezayneb.ecom.repository.ProductRepository;
import com.codezayneb.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());

        if(optionalCartItems.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already in cart");
        } else {
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            if(optionalProduct.isPresent() && optionalUser.isPresent()) {
                CartItems cart = new CartItems();
                cart.setProduct(optionalProduct.get());
                cart.setPrice(optionalProduct.get().getPrice());
                cart.setQuantity(1L);
                cart.setUser(optionalUser.get());
                cart.setOrder(activeOrder);

                CartItems updatedCart = cartItemsRepository.save(cart);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
                activeOrder.getCartItems().add(cart);
                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(mapToCartItemsDto(updatedCart));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }
        }
    }
public OrderDto getCartByUserId (Long userId){
    Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
    List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems().stream().map(CartItems::getCartDto).collect(Collectors.toUnmodifiableList());
    OrderDto orderDto = new OrderDto();
    orderDto.setAmount(activeOrder.getAmount());
    orderDto.setId(activeOrder.getId());
    orderDto.setOrderStatus(activeOrder.getOrderStatus());
    orderDto.setDiscount(activeOrder.getDiscount());
    orderDto.setTotalAmount(activeOrder.getTotalAmount());
    orderDto.setCartItems(cartItemsDtoList);
    return orderDto;

}

    //louta mohamed
    private CartItemsDto mapToCartItemsDto(CartItems cartItems) {
        CartItemsDto cartItemsDto = new CartItemsDto();
        cartItemsDto.setId(cartItems.getId());
        cartItemsDto.setPrice(cartItems.getPrice());
        cartItemsDto.setQuantity(cartItems.getQuantity());
        cartItemsDto.setProductId(cartItems.getProduct().getId());
        cartItemsDto.setOrderId(cartItems.getOrder().getId());
        cartItemsDto.setProductName(cartItems.getProduct().getName());
        cartItemsDto.setUserId(cartItems.getUser().getId());

        if(cartItems.getProduct().getImg() != null) {
            cartItemsDto.setReturnedImg(cartItems.getProduct().getImg());
        }

        return cartItemsDto;
    }
}