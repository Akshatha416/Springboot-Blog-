package com.springboot.blog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.playload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;

@Service
public class CommentServiceImp  implements CommentService{

	
	 private CommentRepository commentRepository;
	 
	 
	 private PostRepository postRepository;
	 private ModelMapper mapper;
	 
	 public CommentServiceImp(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper)
	 {
		 this.commentRepository=commentRepository;
		 this.postRepository=postRepository;
		 this.mapper=mapper;
	 }
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment=new Comment();
		comment=mapToEntity(commentDto);
		Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
		
		comment.setPost(post);
		Comment newcomment= commentRepository.save(comment);
		return mapToDto(newcomment);
	}
	
	
	@Override
	public List<CommentDto> findByPostId(long postId) {
		
		
		Post post =postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
		
		
		List<Comment> comments=commentRepository.findByPostId(postId);
		
		//converting list of comment entity to commentdto's
		List<CommentDto> commentDtos=comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());
		return commentDtos;
	}
	
	
	@Override
	public CommentDto findCommentbyId(long postId, long commentId) {

		Post post =postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
		
		Comment comment=commentRepository.findById(commentId).
				orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
		
		if(!comment.getPost().getId().equals(post.getId()))
		{
			 throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment doesn't belong to post:");
		}
		return mapToDto(comment);
	}
	
	
	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
		
		
		Post post=postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
		
		Comment comment=commentRepository.findById(commentId).
				orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			 throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to Post:");
		}
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		
		Comment newcomment=commentRepository.save(comment);
		return mapToDto(newcomment);
	}
	
	
	
	@Override
	public void deleteComment(long postId, long commentId) {
		Post post= postRepository.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post","id", postId));
		
		Comment comment=commentRepository.findById(commentId).
				orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
		
		if(!comment.getPost().getId().equals(post.getId()))
				{
			  throw new BlogApiException(HttpStatus.BAD_REQUEST,"comment does not belong to post");
				}
		
		
		commentRepository.delete(comment);
	}
	
	private CommentDto mapToDto(Comment comment) {
		
		CommentDto commentDto=mapper.map(comment, CommentDto.class);
		
//		CommentDto commentDto=new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setName(comment.getName());
//		commentDto.setBody(comment.getBody());
		
		return commentDto;
		
		}
	private Comment mapToEntity(CommentDto commentDto)
	{
		
		Comment comment=mapper.map(commentDto, Comment.class);
//		Comment comment=new Comment();
//		comment.setId(commentDto.getId());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());
//		comment.setName(commentDto.getEmail());
		return comment;
		
	}

	
	

}
