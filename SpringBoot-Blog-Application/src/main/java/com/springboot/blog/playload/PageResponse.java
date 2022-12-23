package com.springboot.blog.playload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {
	
	private List<PostDto> content;
	private int pageNo;
	private int pageSize;
	private long totalElements;
	private int totalpages;
	private boolean last;
	

}
