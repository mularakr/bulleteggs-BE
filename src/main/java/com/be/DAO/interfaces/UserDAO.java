package com.be.DAO.interfaces;

import java.util.List;

import com.be.entity.EggPricePerLocationDTO;
import com.be.entity.Location;
import com.be.entity.UpdateEggRates;
import com.be.entity.User;

public interface UserDAO {

	public Long saveUserDetail(User user);
	
	public Long userLogin(String mobileNumber , String password);
	
	public List<User> getUserData();
	
	public List<EggPricePerLocationDTO> getEggRates(boolean prev);
	
	public User getUserDataById(Long userId);
	
	
	public User getUserDataByMobile(String mobileNumber);
	
	public boolean validateOtp(Long userId, String otp);

	public List<Location> getLocations();

	public void updatePrices(UpdateEggRates updateEggRates);
}
