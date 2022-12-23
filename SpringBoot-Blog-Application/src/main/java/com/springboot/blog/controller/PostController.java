package com.springboot.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.playload.PageResponse;
import com.springboot.blog.playload.PostDto;
import com.springboot.blog.service.PostService;
import com.springboot.blog.util.AppConstant;



@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	
	@PostMapping
	
	public ResponseEntity<PostDto>createPost(@Valid @RequestBody PostDto postDto)
	{
		return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.CREATED);
	}
	
	
	@GetMapping
	
	public PageResponse getAllPosts(
			@RequestParam(value="pageNo",defaultValue=AppConstant.DEFAULT_PAGE_NO, required=false ) int pageNo, 
			@RequestParam(value="pageSize",defaultValue=AppConstant.DEFAULT_PAGE_SIZE,required=false) int pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstant.DEFAULT_SORT_BY,required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstant.DEFAULT_SORT_DIR,required=false)String sortDir){
		
		return postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
	}
	
	@GetMapping("/{id}")
	
	public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id) {
		
		return ResponseEntity.ok(postService.getPostById(id));
	}
	
	@PutMapping("/{id}")
	
	public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable(name="id")long id){
		
		PostDto res=postService.updatePostById(postDto, id);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(name="id")long id)
	{
		postService.deleteById(id);
		return new ResponseEntity<>("Post deleted successfully",HttpStatus.OK);
	}

}
