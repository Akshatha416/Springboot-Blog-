package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.playload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(long postId,CommentDto commentDto);
	
	List<CommentDto>findByPostId(long postId);
    CommentDto findCommentbyId(long postId, long commentid);
    CommentDto updateComment(long postId, long commentId,CommentDto commentDto);
    
     void deleteComment(long postId,long commentId);
}
