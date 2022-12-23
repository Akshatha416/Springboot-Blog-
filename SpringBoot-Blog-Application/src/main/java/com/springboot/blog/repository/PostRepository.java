package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
