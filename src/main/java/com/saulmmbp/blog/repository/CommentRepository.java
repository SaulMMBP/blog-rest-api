package com.saulmmbp.blog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.saulmmbp.blog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	Page<Comment> findAllByPostId(Long postId, Pageable pageable);
	
	Optional<Comment> findByIdAndPostId(Long id, Long postId);
	
	boolean existsByIdAndPostId(Long id, Long postId);
	
}
