package com.be.DAO.interfaces;

public interface TokenDAO {
	
	public void saveUserEmail(String email , Long adminId);
	
	public boolean updateToken(String email , String authenticationToken , String secretKey);
	
	public Long getTokenDetail(String email );

	public Long tokenAuthentication(String token , Long emailId);

}
