package com.saulmmbp.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.saulmmbp.blog.dto.CommentModel;
import com.saulmmbp.blog.dto.Message;
import com.saulmmbp.blog.entity.Comment;
import com.saulmmbp.blog.entity.Post;
import com.saulmmbp.blog.exception.BlogAPIException;
import com.saulmmbp.blog.exception.ResourceNotFoundException;
import com.saulmmbp.blog.repository.CommentRepository;
import com.saulmmbp.blog.repository.PostRepository;
import com.saulmmbp.blog.service.CommentService;
import com.saulmmbp.blog.utils.modelAssembler.CommentModelAssembler;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private CommentModelAssembler commentModelAssembler;
	private PagedResourcesAssembler<Comment> pagedResourcesAssembler;
	private ModelMapper mapper;
	
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, 
			CommentModelAssembler commentModelAssembler, PagedResourcesAssembler<Comment> pagedResourcesAssembler,
			ModelMapper mapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.commentModelAssembler = commentModelAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.mapper = mapper;
	}

	@Override
	public CommentModel create(CommentModel commentRequest, Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", String.valueOf(postId)));
		Comment comment = mapToComment(commentRequest, post);
		Comment newComment = commentRepository.save(comment);
		CommentModel commentModel = commentModelAssembler.toModel(newComment);
		return commentModel;
	}
	
	@Override
	public PagedModel<CommentModel> getAllByPostId(Long postId, Pageable pageable) {
		if (!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post", "Id", String.valueOf(postId));
		Page<Comment> comments = commentRepository.findAllByPostId(postId, pageable);
		PagedModel<CommentModel> pagedModel = pagedResourcesAssembler.toModel(comments, commentModelAssembler);
		return pagedModel;
	}
	
	@Override
	public CommentModel get(Long id, Long postId) {
		if (!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post", "Id", String.valueOf(postId));
		if (!commentRepository.existsById(id)) throw new ResourceNotFoundException("Comment", "Id", String.valueOf(id));
		Comment comment = commentRepository.findByIdAndPostId(id, postId).orElseThrow(() -> new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post"));
		CommentModel commentModel = commentModelAssembler.toModel(comment);
		return commentModel;
	}
	
	@Override
	public CommentModel update(CommentModel commentRequest, Long id, Long postId) {
		if (!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post", "Id", String.valueOf(postId));
		if (!commentRepository.existsById(id)) throw new ResourceNotFoundException("Comment", "Id", String.valueOf(id));
		Comment comment = commentRepository.findByIdAndPostId(id, postId).orElseThrow(() -> new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post"));
		comment.setName(commentRequest.getName());
		comment.setBody(commentRequest.getBody());
		comment.setEmail(commentRequest.getEmail());
		Comment updatedComment = commentRepository.save(comment);
		CommentModel commentModel = commentModelAssembler.toModel(updatedComment);
		return commentModel;
	}
	
	@Override
	public Message delete(Long id, Long postId) {
		if (!postRepository.existsById(postId)) throw new ResourceNotFoundException("Post", "Id", String.valueOf(postId));
		if (!commentRepository.existsById(id)) throw new ResourceNotFoundException("Comment", "Id", String.valueOf(id));
		if (!commentRepository.existsByIdAndPostId(id, postId)) throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
		commentRepository.deleteById(id);
		return new Message("Comment deleted successfully!.");
	}

	private Comment mapToComment(CommentModel commentModel, Post post) {
		Comment comment = mapper.map(commentModel, Comment.class);
		comment.setPost(post);
		return comment;
	}
}
