package com.be.restController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.entity.Post;
import com.be.entity.User;
import com.be.service.implementation.PostService;
import com.be.service.interfaces.TokenService;
import com.be.service.interfaces.UserService;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Authorization")
public class PostController {

	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@GetMapping(path = "/getPosts/{userId}")
	public ResponseEntity<List<Post>> getPosts(@PathVariable Long userId, @RequestHeader("Authorization") String authorizationToken){
		String token[] = authorizationToken.split(" ");
		List<Post> posts = new ArrayList<Post>();
		long result = tokenService.tokenAuthentication(token[1], userId);
	
		if (result > 0) {
			User user = userService.getUserData(userId);
			if(user.getRole().equalsIgnoreCase("Farmer")) {
				posts = postService.getPostsById(userId);
			}else {
				posts = postService.getPosts();
			}
		}
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);	
	}
	
	/*
	 * @GetMapping(path = "/getPostsById/{userId}") public
	 * ResponseEntity<List<Post>> getPostsById(@PathVariable Long
	 * userId, @RequestHeader("Authorization") String authorizationToken){ String
	 * token[] = authorizationToken.split(" "); List<Post> posts = null; long result
	 * = tokenService.tokenAuthentication(token[1], userId);
	 * 
	 * if (result > 0) { posts = postService.getPostsById(userId); } return new
	 * ResponseEntity<List<Post>>(posts, HttpStatus.OK); }
	 */
	
	@PostMapping(path = "/{userId}")
	public ResponseEntity<List<Post>> setPosts(@PathVariable Long userId, @RequestHeader("Authorization") String authorizationToken, @RequestBody Post post){
		String token[] = authorizationToken.split(" ");
		long result = tokenService.tokenAuthentication(token[1], userId);
	
		if (result > 0) {
			User user = userService.getUserData(userId);
			if(user.getRole().equalsIgnoreCase("farmer")|| user.getRole().equalsIgnoreCase("admin")) {
				post.setUser(user);
				postService.savePost(post);
				return new ResponseEntity<List<Post>>(HttpStatus.OK);	
			}
			
		}
		return new ResponseEntity<List<Post>>(HttpStatus.UNAUTHORIZED);	
	}
	
	@DeleteMapping(path = "/deletePost/{postId}/{userId}")
	public ResponseEntity<Void> deletePost(@PathVariable Long postId, @PathVariable Long userId, @RequestHeader("Authorization") String authorizationToken){
		String token[] = authorizationToken.split(" ");
		long result = tokenService.tokenAuthentication(token[1], userId);
	
		if (result > 0) {
			User user = userService.getUserData(userId);
			if(user.getRole().equalsIgnoreCase("farmer")|| user.getRole().equalsIgnoreCase("admin")) {
				postService.deletePost(postId);
				return new ResponseEntity<>(HttpStatus.OK);	
			}
			
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);	
	}
	
	@PutMapping(path = "/markAsSold/{postId}/{userId}")
	public ResponseEntity<Boolean> markAsSold(@PathVariable Long postId, @PathVariable Long userId, @RequestHeader("Authorization") String authorizationToken){
		String token[] = authorizationToken.split(" ");
		long result = tokenService.tokenAuthentication(token[1], userId);
	
		if (result > 0) {
			User user = userService.getUserData(userId);
			if(user.getRole().equalsIgnoreCase("farmer")|| user.getRole().equalsIgnoreCase("admin")) {
				return new ResponseEntity<>(postService.markAsSold(postId), HttpStatus.OK);	
			}
			
		}
		return new ResponseEntity<>(false,HttpStatus.UNAUTHORIZED);	
	}
}
