package com.codezayneb.ecom.repository;


//import org.springframework.core.annotation.Order;
import com.codezayneb.ecom.entity.Order;
import com.codezayneb.ecom.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus status);



}
