package com.saulmmbp.blog.utils.modelAssembler;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.saulmmbp.blog.controller.PostController;
import com.saulmmbp.blog.dto.CommentModel;
import com.saulmmbp.blog.dto.PostModel;
import com.saulmmbp.blog.entity.Post;

@Component
public class PostModelAssembler implements RepresentationModelAssembler<Post, PostModel> {
	
	private CommentModelAssembler commentModelAssembler;
	private ModelMapper mapper;
	
	public PostModelAssembler(CommentModelAssembler commentModelAssembler, ModelMapper mapper) {
		this.commentModelAssembler = commentModelAssembler;
		this.mapper = mapper;
	}

	@Override
	public PostModel toModel(Post entity) {
		PostModel postModel = mapper.map(entity, PostModel.class);
		
		if (entity.getCategory() != null) postModel.setCategoryId(entity.getCategory().getId());
		
		if (entity.getComments() != null) {
			Set<CommentModel> comments = entity.getComments().stream().map(commentModelAssembler::toModel).collect(Collectors.toSet());
			postModel.setComments(comments);
		} 
		
		postModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).get(entity.getId())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).getAll(Pageable.ofSize(10))).withRel("posts"));
		return postModel;
	}

}
