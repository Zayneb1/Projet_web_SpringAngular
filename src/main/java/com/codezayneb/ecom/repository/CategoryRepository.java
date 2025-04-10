package com.codezayneb.ecom.repository;

import com.codezayneb.ecom.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository




public interface CategoryRepository extends JpaRepository<Category, Long> {

}
