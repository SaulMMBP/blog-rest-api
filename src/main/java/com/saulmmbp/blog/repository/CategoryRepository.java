package com.saulmmbp.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saulmmbp.blog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
