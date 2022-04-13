package com.be.service.interfaces;

import java.util.List;

import com.be.entity.EggPricePerLocationDTO;
import com.be.entity.Location;
import com.be.entity.UpdateEggRates;
import com.be.entity.User;

public interface UserService {

	public Long saveUserDetail(User userDetail);
	
	public Long userLogin(String emailId , String password);
	
	public List<User> getUserData();
	
	public User getUserData(Long id);
	
	public User getUserData(String id);
	
	public List<EggPricePerLocationDTO> getEggRates();
	
	public List<EggPricePerLocationDTO> getYesterdayEggRates();
	
	public boolean validateOtp(Long userId, String otp);
	
	public String generateOtp();
	
	public boolean saveOtp(String mobileNumber, String otp);
	
	public List<Location> getLocations();

	public void updatePrices(UpdateEggRates updateEggRates);
}
