package com.springboot.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.playload.CommentDto;
import com.springboot.blog.service.CommentService;

@RestController
//@RequestMapping("/api")
@RequestMapping("/api")
public class CommentController {
	
	private CommentService commentService;
	
	public CommentController( CommentService commentService) {
		this.commentService=commentService;
	}
	
	
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId")  long postId,
			@Valid @RequestBody CommentDto commentDto){
		
		
		return new ResponseEntity<>(commentService.createComment(postId, commentDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable(value="postId") long postId){
		
		return commentService.findByPostId(postId);
	}

	@GetMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable(value="postId")long postId,@PathVariable(value="id")long commentId){
		return new ResponseEntity<>(commentService.findCommentbyId(postId, commentId),HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value="postId") long postId,
			@PathVariable(value="id") long commentId,@Valid @RequestBody CommentDto commentDto){
		return new ResponseEntity<>(commentService.updateComment(postId,commentId,commentDto),HttpStatus.OK); 
	}
	
	@DeleteMapping("/posts/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable long postId, @PathVariable(value="id") long commentId )
	{
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("deleted the comment",HttpStatus.OK);
	}
}
