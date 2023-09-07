package com.saulmmbp.blog.service;

import org.springframework.hateoas.CollectionModel;

import com.saulmmbp.blog.dto.CategoryModel;
import com.saulmmbp.blog.dto.Message;

public interface CategoryService {

	CategoryModel create(CategoryModel categoryRequest);
	
	CollectionModel<CategoryModel> getAll();
	
	CategoryModel getById(Long id);
	
	CategoryModel update(Long id, CategoryModel categoryRequest);
	
	Message delete(Long id);
	
}
