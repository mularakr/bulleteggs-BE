package com.be.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.DAO.interfaces.TokenDAO;
import com.be.service.interfaces.TokenService;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenDAO tokenDAO;
	
	@Transactional
	public void saveUserEmail(String mobileNumber, Long userId) {
		tokenDAO.saveUserEmail(mobileNumber, userId);
	}

	@Transactional
	public boolean updateToken(String mobileNumber, String authenticationToken, String secretKey) {
		return tokenDAO.updateToken(mobileNumber, authenticationToken, secretKey);
	}

	@Transactional
	public Long getTokenDetail(String mobileNumber) {
		return tokenDAO.getTokenDetail(mobileNumber);
	}

	@Transactional
	public Long tokenAuthentication(String token, Long userId) {
		return tokenDAO.tokenAuthentication(token, userId);
	}

}
