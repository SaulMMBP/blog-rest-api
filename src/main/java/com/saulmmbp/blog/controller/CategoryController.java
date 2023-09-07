package com.saulmmbp.blog.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saulmmbp.blog.dto.CategoryModel;
import com.saulmmbp.blog.dto.Message;
import com.saulmmbp.blog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	private CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CategoryModel> create(@Valid @RequestBody CategoryModel categoryRequest) {
		return new ResponseEntity<>(categoryService.create(categoryRequest), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<CollectionModel<CategoryModel>> getAll() {
		return ResponseEntity.ok(categoryService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryModel> getById(@PathVariable Long id) {
		return ResponseEntity.ok(categoryService.getById(id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<CategoryModel> update(@PathVariable Long id, @Valid @RequestBody CategoryModel categoryModel) {
		return ResponseEntity.ok(categoryService.update(id, categoryModel));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Message> delete(@PathVariable Long id) {
		return ResponseEntity.ok(categoryService.delete(id));
	}
}
