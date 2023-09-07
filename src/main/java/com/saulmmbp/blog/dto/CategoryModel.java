package com.saulmmbp.blog.dto;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "categories")
public class CategoryModel extends RepresentationModel<CategoryModel> {
	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
}
