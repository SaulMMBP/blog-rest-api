package com.saulmmbp.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.saulmmbp.blog.dto.Message;
import com.saulmmbp.blog.dto.PostModel;
import com.saulmmbp.blog.entity.Category;
import com.saulmmbp.blog.entity.Post;
import com.saulmmbp.blog.exception.ResourceNotFoundException;
import com.saulmmbp.blog.repository.CategoryRepository;
import com.saulmmbp.blog.repository.PostRepository;
import com.saulmmbp.blog.service.PostService;
import com.saulmmbp.blog.utils.modelAssembler.PostModelAssembler;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private CategoryRepository categoryRepository;
	private PostModelAssembler postModelAssembler;
	private PagedResourcesAssembler<Post> pagedResourcesAssembler;
	private ModelMapper mapper;
	
	public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository,
			PostModelAssembler postModelAssembler, PagedResourcesAssembler<Post> pagedResourcesAssembler,
			ModelMapper mapper) {
		this.postRepository = postRepository;
		this.categoryRepository = categoryRepository;
		this.postModelAssembler = postModelAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.mapper = mapper;
	}

	@Override
	public PostModel create(PostModel postRequest) {
		Post post = mapper.map(postRequest, Post.class);
		Category category = categoryRepository.findById(postRequest.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", String.valueOf(postRequest.getCategoryId())));
		post.setCategory(category);
		Post newPost = postRepository.save(post);
		PostModel postModel = postModelAssembler.toModel(newPost);
		return postModel;
	}

	@Override
	public PagedModel<PostModel> getAll(Pageable pageable) {
		Page<Post> posts = postRepository.findAll(pageable);
		PagedModel<PostModel> pagedModel = pagedResourcesAssembler.toModel(posts, postModelAssembler);
		return pagedModel;
	}
	
	@Override
	public PostModel get(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", String.valueOf(id)));
		PostModel postModel = postModelAssembler.toModel(post);
		return postModel;
	}
	
	@Override
	public PostModel update(PostModel postRequest, Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", String.valueOf(id)));
		post.setTitle(postRequest.getTitle());
		post.setDescription(postRequest.getDescription());
		post.setContent(postRequest.getContent());
		
		Category category = categoryRepository.findById(postRequest.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", String.valueOf(postRequest.getCategoryId())));
		post.setCategory(category);
		
		Post updatedPost = postRepository.save(post);
		PostModel postModel = postModelAssembler.toModel(updatedPost);
		return postModel;
	}
	
	@Override
	public Message delete(Long id) {
		if (!postRepository.existsById(id)) throw new ResourceNotFoundException("Post", "Id", String.valueOf(id));
		postRepository.deleteById(id);
		return new Message("Post deleted successfully!.");
	}

	@Override
	public PagedModel<PostModel> getAllByCategoryId(Long categoryId, Pageable pageable) {
		if (!categoryRepository.existsById(categoryId)) throw new ResourceNotFoundException("Category", "Id", String.valueOf(categoryId));
		Page<Post> posts = postRepository.findAllByCategoryId(categoryId, pageable);
		PagedModel<PostModel> pagedModel = pagedResourcesAssembler.toModel(posts, postModelAssembler);
		return pagedModel;
	}
}
