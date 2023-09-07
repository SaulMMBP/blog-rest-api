package com.saulmmbp.blog.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saulmmbp.blog.dto.CommentModel;
import com.saulmmbp.blog.dto.Message;
import com.saulmmbp.blog.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	@PostMapping
	public ResponseEntity<CommentModel> create(@Valid @RequestBody CommentModel commentRequest, @PathVariable Long postId) {
		return new ResponseEntity<>(commentService.create(commentRequest, postId), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<PagedModel<CommentModel>> getAllByPostId(@PathVariable Long postId, @PageableDefault Pageable pageable) {
		return ResponseEntity.ok(commentService.getAllByPostId(postId, pageable));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CommentModel> get(@PathVariable Long id, @PathVariable Long postId) {
		return ResponseEntity.ok(commentService.get(id, postId));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CommentModel> update(@Valid @RequestBody CommentModel commentRequest, @PathVariable Long id, @PathVariable Long postId) {
		return ResponseEntity.ok(commentService.update(commentRequest, id, postId));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Message> delete(@PathVariable Long id, @PathVariable Long postId) {
		return ResponseEntity.ok(commentService.delete(id, postId));
	}
}
