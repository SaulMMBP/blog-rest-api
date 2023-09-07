package com.saulmmbp.blog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.saulmmbp.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	Page<Post> findAllByCategoryId(Long categoryId, Pageable pageable);
	
}