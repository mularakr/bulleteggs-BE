package com.be.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Token")
public class Token {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="token_id")
	private Long tokenID;
	
	@Column(name="user_id" , unique=true)
	private Long userID;
	 
	@Column(name="authenticationToken")
	private String authenticationToken;
	
	@Column(name="secretKey")
	private String secretKey;
	
	@Column(name="mobile_number")
	private String mobileNumber;
	
	public Token() { }

	public Token(Long tokenID, Long userID, String authenticationToken, String secretKey, String mobileNumber) {
		super();
		this.tokenID = tokenID;
		this.userID = userID;
		this.authenticationToken = authenticationToken;
		this.secretKey = secretKey;
		this.mobileNumber = mobileNumber;
	}

	public Long getTokenID() {
		return tokenID;
	}

	public void setTokenID(Long tokenID) {
		this.tokenID = tokenID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	

	@Override
	public String toString() {
		return "Token [tokenID=" + tokenID + ", userID=" + userID + ", authenticationToken=" + authenticationToken
				+ ", secretKey=" + secretKey + ", mobileNumber=" + mobileNumber + "]";
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	
}
