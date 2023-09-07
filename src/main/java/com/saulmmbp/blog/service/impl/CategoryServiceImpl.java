package com.saulmmbp.blog.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import com.saulmmbp.blog.dto.CategoryModel;
import com.saulmmbp.blog.dto.Message;
import com.saulmmbp.blog.entity.Category;
import com.saulmmbp.blog.exception.ResourceNotFoundException;
import com.saulmmbp.blog.repository.CategoryRepository;
import com.saulmmbp.blog.service.CategoryService;
import com.saulmmbp.blog.utils.modelAssembler.CategoryModelAssembler;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;
	private CategoryModelAssembler categoryModelAssembler;
	private ModelMapper modelMapper;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryModelAssembler categoryModelAssembler,
			ModelMapper modelMapper) {
		this.categoryRepository = categoryRepository;
		this.categoryModelAssembler = categoryModelAssembler;
		this.modelMapper = modelMapper;
	}

	@Override
	public CategoryModel create(CategoryModel categoryRequest) {
		Category category = modelMapper.map(categoryRequest, Category.class);
		Category newCategory = categoryRepository.save(category);
		CategoryModel categoryModel = categoryModelAssembler.toModel(newCategory);
		return categoryModel;
	}

	@Override
	public CollectionModel<CategoryModel> getAll() {
		List<Category> categories = categoryRepository.findAll();
		CollectionModel<CategoryModel> collectionModel = categoryModelAssembler.toCollectionModel(categories);
		return collectionModel;
	}

	@Override
	public CategoryModel getById(Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", String.valueOf(id)));
		CategoryModel categoryModel = categoryModelAssembler.toModel(category);
		return categoryModel;
	}

	@Override
	public CategoryModel update(Long id, CategoryModel categoryRequest) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", String.valueOf(id)));
		category.setName(categoryRequest.getName());
		category.setDescription(category.getDescription());
		Category updatedCategory = categoryRepository.save(category);
		CategoryModel categoryModel = categoryModelAssembler.toModel(updatedCategory);
		return categoryModel;
	}

	@Override
	public Message delete(Long id) {
		if (!categoryRepository.existsById(id)) throw new ResourceNotFoundException("Category", "Id", String.valueOf(id));
		categoryRepository.deleteById(id);
		return new Message("Category deleted successfully!.");
	}

}
