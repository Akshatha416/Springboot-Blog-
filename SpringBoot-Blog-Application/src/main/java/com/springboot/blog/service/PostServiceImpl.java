package com.springboot.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.playload.PageResponse;
import com.springboot.blog.playload.PostDto;
import com.springboot.blog.repository.PostRepository;


@Service
public class PostServiceImpl implements PostService {
	
	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	PostServiceImpl(PostRepository postRepository,ModelMapper mapper){
		this.postRepository=postRepository;
		this.mapper=mapper;
	}
	
	
	@Override
	public PostDto createPost(PostDto postDto) {
		//converting DTO to Entity
		Post post =mapToEntity(postDto);
		
		Post p=postRepository.save(post);
		
		// converting Entity to Dto
		PostDto postdtoobj=mapToDto(p);
		
		return postdtoobj;
	}
	
	
	@Override
	public PageResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir) {
		
		//List<Post> posts =postRepository.findAll();
		
		//this below line is only for paging
		//Pageable pageable=PageRequest.of(pageNo, pageSize);
		
		Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable pageable=PageRequest.of(pageNo, pageSize, sort);
		Page<Post> posts=postRepository.findAll(pageable);
		
		List<Post> listpost=posts.getContent();
		
		List<PostDto>content= listpost.stream().map(p->mapToDto(p)).collect(Collectors.toList());
		
		PageResponse pageResponse=new PageResponse();
		pageResponse.setContent(content);
		pageResponse.setPageNo(posts.getNumber());
		pageResponse.setPageSize(posts.getSize());
		pageResponse.setTotalElements(posts.getTotalElements());
		pageResponse.setTotalpages(posts.getTotalPages());
		pageResponse.setLast(posts.isLast());
		
		
		return pageResponse;
	}
	
	
	@Override
	public PostDto getPostById(Long id) {
		
		Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id", id) );
		return mapToDto(post);
	}
	
	
	

	@Override
	public PostDto updatePostById(PostDto postDto, long id) {
		
		Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setDescription(postDto.getDescription());
		
		Post p=postRepository.save(post);
	
	return mapToDto(p);
		
		
	}

	@Override
	public void deleteById(long id) {
		Post post =postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
		postRepository.delete(post);
    
	}

	
	
	
	
    //map to Dto
	private PostDto mapToDto(Post p) {
		
		
		PostDto postdtoobj=mapper.map(p, PostDto.class);
//		PostDto postdtoobj =new PostDto();
//		postdtoobj.setId(p.getId());
//		postdtoobj.setTitle(p.getTitle());
//		postdtoobj.setContent(p.getContent());
//		postdtoobj.setDescription(p.getDescription());
		
		return postdtoobj;
	}

    //Map to Entity
	private Post mapToEntity(PostDto postDto) {
		
		
		Post post=mapper.map(postDto, Post.class);
//	    Post post=new Post();
//		post.setId(postDto.getId());
//		post.setTitle(postDto.getTitle());
//		post.setContent(postDto.getContent());
//		post.setDescription(postDto.getDescription());
		
		return post;
	}


	



	


	
	

}
