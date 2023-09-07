package com.saulmmbp.blog.dto;

import java.util.Set;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "posts")
@JsonInclude(Include.NON_EMPTY)
public class PostModel extends RepresentationModel<PostModel> {
	
	private Long id;
	
	@NotBlank
	@Size(min = 2, message = "Post title should have at least 2 characters")
	private String title;
	
	@NotBlank
	@Size(min = 10, message = "Post description should have at least 10 characters")
	private String description;
	
	@NotBlank
	private String content;
	
	@NotNull
	private Long categoryId;
	
	private Set<CommentModel> comments;
}
