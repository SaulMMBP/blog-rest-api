package com.saulmmbp.blog.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
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

import com.saulmmbp.blog.dto.Message;
import com.saulmmbp.blog.dto.PostModel;
import com.saulmmbp.blog.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {

	private PostService postService;
	
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostModel> create(@Valid @RequestBody PostModel postRequest) {
		return new ResponseEntity<>(postService.create(postRequest), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<PagedModel<PostModel>> getAll(@PageableDefault Pageable pageable) {
		return ResponseEntity.ok(postService.getAll(pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PostModel> get(@PathVariable Long id) {
		return ResponseEntity.ok(postService.get(id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostModel> update(@Valid @RequestBody PostModel postRequest, @PathVariable Long id) {
		return ResponseEntity.ok(postService.update(postRequest, id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Message> delete(@PathVariable Long id) {
		return ResponseEntity.ok(postService.delete(id));
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<PagedModel<PostModel>> getAllByCategory(@PathVariable Long categoryId, @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(postService.getAllByCategoryId(categoryId, pageable));
	}
}
