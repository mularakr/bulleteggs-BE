package com.be.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.DAO.implementation.ProfileDAOImpl;
import com.be.entity.Profile;

@Service
public class ProfileServiceImpl {

	@Autowired
	ProfileDAOImpl profileDAOImpl;

	public void createProfile(Profile profile) {
		profileDAOImpl.saveProfile(profile);
	}
	
	public List<Profile> getProfiles(Long userId) {
		return profileDAOImpl.getProfiles(userId);
	}
}
