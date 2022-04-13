package com.be.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jetbrains.annotations.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private Long userID;
	
	@Column(name="email_id" , unique=true)
	@NotNull
	public String emailId;
	
	@Column
	@NotNull
	public String mobileNumber;
	
	@Column(name="full_name")
	@NotNull
	public String fullName;
	
	@Column(name="password")
	@NotNull
	@JsonProperty(access = Access.WRITE_ONLY)
	public String password;
	
	@Column(name="role" , nullable = false)
	@NotNull
	public String role;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	Set<Post> posts;
	
	@OneToMany(mappedBy = "user")
	Set<Profile> profiles;
	
	public User() { }

	public User(Long userID, String emailId, String fullName, String password, String role) {
		super();
		this.userID = userID;
		this.emailId = emailId;
		this.fullName = fullName;
		this.password = password;
		this.role = role;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId.toLowerCase();
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
