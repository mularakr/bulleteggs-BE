package com.be.service.implementation;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.DAO.implementation.PostDAOImpl;
import com.be.entity.Post;

@Service
public class PostService {

	@Autowired
	PostDAOImpl postDAOImpl;
	
	@Transactional
	public List<Post> getPosts(){
		return postDAOImpl.getPosts();
	}
	@Transactional
	public List<Post> getPostsById(Long userId){
		List<Post> posts = postDAOImpl.getPosts(userId);
		if(posts!=null && posts.size()>0) {Collections.sort(posts);}
		return posts;
	}
	
	@Transactional
	public void savePost(Post post){
		postDAOImpl.savePost(post);
	}
	
	@Transactional
	public void deletePost(Long postId){
		postDAOImpl.deletePost(postId);
	}
	@Transactional
	public boolean markAsSold(Long postId) {
		return postDAOImpl.markAsSold(postId);
		
	}
}
