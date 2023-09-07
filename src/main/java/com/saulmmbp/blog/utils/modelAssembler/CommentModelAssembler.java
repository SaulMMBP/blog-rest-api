package com.saulmmbp.blog.utils.modelAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.saulmmbp.blog.controller.CommentController;
import com.saulmmbp.blog.controller.PostController;
import com.saulmmbp.blog.dto.CommentModel;
import com.saulmmbp.blog.entity.Comment;

@Component
public class CommentModelAssembler implements RepresentationModelAssembler<Comment, CommentModel> {

	private ModelMapper mapper;
	
	public CommentModelAssembler(ModelMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public CommentModel toModel(Comment entity) {
		CommentModel commentModel = mapper.map(entity, CommentModel.class);
		
		commentModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).get(entity.getId(), entity.getPost().getId())).withSelfRel(),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PostController.class).get(entity.getPost().getId())).withRel("post"),
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentController.class).getAllByPostId(entity.getPost().getId(), Pageable.ofSize(10))).withRel("comments"));
		return commentModel;
	}

}
