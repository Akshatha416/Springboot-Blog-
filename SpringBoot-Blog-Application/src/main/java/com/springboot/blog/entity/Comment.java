package com.springboot.blog.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String name;
	private String email;
	private String body;
	
	
	//FetchType.LAZY is tells Hibernate to only fetch the related entities from the database when you use the relationship  
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="Post_Id",nullable=false) // @JoinColumn is used to specify foreignKey
	private Post post;

}
