package com.codezayneb.ecom.dto;


import com.codezayneb.ecom.entity.CartItems;
import com.codezayneb.ecom.entity.User;
import com.codezayneb.ecom.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private Long id;
    private String orderDescription;
    private Date date;

    private Long amount;
    private String address;

    private String payment;
    private OrderStatus orderStatus ;

    private Long totalAmount;
    private Long discount;
    private UUID trackingId;


    private String userName;

    private List<CartItemsDto> CartItems;
}
