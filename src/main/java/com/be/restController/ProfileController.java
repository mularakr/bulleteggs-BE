package com.be.restController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.entity.Profile;
import com.be.entity.User;
import com.be.service.implementation.ProfileServiceImpl;
import com.be.service.interfaces.TokenService;
import com.be.service.interfaces.UserService;

@RestController
@RequestMapping("/profile")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Authorization")
public class ProfileController {

	@Autowired
	ProfileServiceImpl profileServiceImpl;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
	UserService userService;
	
	@PostMapping(path = "/create/{userId}")
	public void createProfile(@PathVariable Long userId,  @RequestHeader("Authorization") String authorizationToken,@RequestBody Profile profile) {
		String token[] = authorizationToken.split(" ");
		long result = tokenService.tokenAuthentication(token[1], userId);
	
		if (result > 0) {
			User user = userService.getUserData(userId);
			if(user.getRole().equalsIgnoreCase("farmer")|| user.getRole().equalsIgnoreCase("admin")) {
				profile.setUser(user);
				profileServiceImpl.createProfile(profile);
			}
		}
	}
	
	@GetMapping(path = "/get/{userId}")
	public List<Profile> getProfiles(@PathVariable Long userId, @RequestHeader("Authorization") String authorizationToken) {
		String token[] = authorizationToken.split(" ");
		long result = tokenService.tokenAuthentication(token[1], userId);
		
		if (result > 0) {
			List<Profile> prfiles = profileServiceImpl.getProfiles(userId);
			if(prfiles==null) {prfiles = new ArrayList<Profile>();}
			return prfiles;
		}
		return null;
		
	}
}
