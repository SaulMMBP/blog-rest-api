package com.saulmmbp.blog.service;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.saulmmbp.blog.dto.CommentModel;
import com.saulmmbp.blog.dto.Message;

public interface CommentService {

	CommentModel create(CommentModel commentRequest, Long postId);
	
	PagedModel<CommentModel> getAllByPostId(Long postId, Pageable pageable);
	
	CommentModel get(Long id, Long postId);
	
	CommentModel update(CommentModel commentRequest, Long id, Long postId);
	
	Message delete(Long id, Long postId);
	
}
