package com.saulmmbp.blog.service;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.saulmmbp.blog.dto.Message;
import com.saulmmbp.blog.dto.PostModel;

public interface PostService {
	
	PostModel create(PostModel postRequest);
	
	PagedModel<PostModel> getAll(Pageable pageable);
	
	PostModel get(Long id);
	
	PostModel update(PostModel postRequest, Long id);
	
	Message delete(Long id);
	
	PagedModel<PostModel> getAllByCategoryId(Long categoryId, Pageable pageable);
	
}
