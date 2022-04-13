package com.be.service.interfaces;

public interface TokenService {
	
	public void saveUserEmail(String mobileNumber , Long userId);
	
	public boolean updateToken(String mobileNumber , String authenticationToken , String secretKey);
	
	public Long getTokenDetail(String mobileNumber );

	public Long tokenAuthentication(String token , Long userId);

}
