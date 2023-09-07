package com.saulmmbp.blog.utils.modelAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.saulmmbp.blog.controller.CategoryController;
import com.saulmmbp.blog.dto.CategoryModel;
import com.saulmmbp.blog.entity.Category;

@Component
public class CategoryModelAssembler implements RepresentationModelAssembler<Category, CategoryModel> {

	private ModelMapper modelMapper;
	
	public CategoryModelAssembler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public CategoryModel toModel(Category entity) {
		CategoryModel categoryModel = modelMapper.map(entity, CategoryModel.class);
		
		categoryModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getById(entity.getId())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAll()).withRel("categories"));
		return categoryModel;
	}

}
