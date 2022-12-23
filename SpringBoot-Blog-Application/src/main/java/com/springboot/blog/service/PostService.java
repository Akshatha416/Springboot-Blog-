package com.springboot.blog.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.springboot.blog.playload.PageResponse;
import com.springboot.blog.playload.PostDto;

public interface PostService {
	
	PostDto createPost(PostDto postDto);
	//List<PostDto> getAllPosts();
	PostDto getPostById(Long id);
	PostDto updatePostById(PostDto postDto,long id );
	
	void deleteById(long id);
	
	PageResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

}
