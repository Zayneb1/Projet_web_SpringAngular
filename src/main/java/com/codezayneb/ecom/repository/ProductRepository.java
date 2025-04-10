package com.codezayneb.ecom.repository;


import com.codezayneb.ecom.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



 List<Product> findAllByNameContaining(String title);


}
