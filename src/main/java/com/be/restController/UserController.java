package com.be.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.entity.EggPricePerLocationDTO;
import com.be.entity.Location;
import com.be.entity.UpdateEggRates;
import com.be.entity.User;
import com.be.service.interfaces.TokenService;
import com.be.service.interfaces.UserService;
import com.be.utils.GenerateToken;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", exposedHeaders = "Authorization")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	GenerateToken generateToken = new GenerateToken();
	
	@PostMapping("/saveUser")
	public ResponseEntity<HttpStatus> saveuser(@RequestBody User user) {
		userService.saveUserDetail(user);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User user){
		long status;
		HttpHeaders httpHeader = null;
	
		// Authenticate User.
		status = userService.userLogin(user.mobileNumber, user.getPassword());
		
		/*
		 * If User is authenticated then Do Authorization Task.
		 */
		if (status > 0) 
		{
			/*
			 * Generate token.
			 */
			String tokenData[] = generateToken.createJWT(user.mobileNumber, "BulletEggs7218384845", "Bullet Eggs JWT Token",
					user.getRole(), 43200000);
			
			// get Token.
			String token = tokenData[0];
			
			//System.out.println("Authorization :: " + token);

			// Create the Header Object
			httpHeader = new HttpHeaders();

			// Add token to the Header.
			httpHeader.add("Authorization", token);

			// Check if token is already exist.
			long isMobileNumberExists = tokenService.getTokenDetail(user.mobileNumber);
			
			/*
			 * If token exist then update Token else create and insert the token.
			 */
			if (isMobileNumberExists > 0) 
			{
				tokenService.updateToken(user.mobileNumber, token, tokenData[1]);
			} 
			else 
			{
				tokenService.saveUserEmail(user.mobileNumber, status);
				tokenService.updateToken(user.mobileNumber, token, tokenData[1]);
			}

			User loggedInUser = userService.getUserData(Long.valueOf(status));
			return new ResponseEntity<User>(loggedInUser, httpHeader, HttpStatus.OK);
		} 
		
		// if not authenticated return  status what we get.
		else 
		{
			User loggedInUser = null;
			return new ResponseEntity<User>(loggedInUser, httpHeader, HttpStatus.UNAUTHORIZED);
		}
		

	}
	
	
	@PostMapping(path = "/updatePrices/{userId}")
	public boolean updatePrices(@PathVariable Long userId,  @RequestHeader("Authorization") String authorizationToken,@RequestBody UpdateEggRates updateEggRates) {
		String token[] = authorizationToken.split(" ");
		long result = tokenService.tokenAuthentication(token[1], userId);
	
		if (result > 0) {
			User user = userService.getUserData(userId);
			if(user.getRole().equalsIgnoreCase("admin")) {
				userService.updatePrices(updateEggRates);
				return true;
			}else {
				return false;
			}
		}
		return false;
	}
	
	
	@GetMapping("/getUserData/{userId}")
	public List<User> getUserData(@PathVariable long userId, @RequestHeader("Authorization") String authorizationToken){
		String token[] = authorizationToken.split(" ");
		long result = tokenService.tokenAuthentication(token[1], userId);
		
		if (result > 0) {
			return userService.getUserData();
		} else {
			return null;
		}
	}
	
	@GetMapping("/getEggPrices")
	public List<EggPricePerLocationDTO> getEggRates(){
		return userService.getEggRates();
	}
	
	@GetMapping("/getYesterdayEggPrices")
	public List<EggPricePerLocationDTO> getYesterdayEggPrices(){
		return userService.getYesterdayEggRates();
	}
	
	@GetMapping("/getLocations")
	public List<Location> getLocations(){
		return userService.getLocations();
	}
	
	
	@GetMapping("/generateOtp")
	public ResponseEntity<Boolean> generateOtp(@RequestParam String mobileNumber){
		
		User user = userService.getUserData(mobileNumber);
		
		if (user!=null && user.mobileNumber !=null) {
			String otp = userService.generateOtp();
			userService.saveOtp(mobileNumber, otp);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		
		return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
	}
	
	@GetMapping("/forgotPassword")
	public ResponseEntity<Boolean> setPassword(@RequestParam Long userId, @RequestParam String otp, @RequestHeader("Authorization")String authorizationToken){
		
		if(userService.validateOtp(userId, otp)) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		
		return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
	}
	
}
