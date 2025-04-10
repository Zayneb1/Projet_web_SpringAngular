package com.codezayneb.ecom.services.admin.category;

import com.codezayneb.ecom.dto.CategoryDto;
import com.codezayneb.ecom.entity.Category;

import java.util.List;

public interface CategoryService {

    Category createcategory(CategoryDto categoryDto);

    List<Category> getAllCategories();
}
