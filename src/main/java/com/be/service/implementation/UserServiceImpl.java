package com.be.service.implementation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.DAO.interfaces.UserDAO;
import com.be.entity.EggPricePerLocationDTO;
import com.be.entity.Location;
import com.be.entity.UpdateEggRates;
import com.be.entity.User;
import com.be.service.interfaces.UserService;


@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;

	@Transactional
	public Long saveUserDetail(User userDetail) {
		return userDAO.saveUserDetail(userDetail);
	}
	
	@Transactional
	public Long userLogin(String emailId, String password) {
		return userDAO.userLogin(emailId, password);
	}

	@Transactional
	public List<User> getUserData() {
		return userDAO.getUserData();
	}
	
	@Transactional
	public List<EggPricePerLocationDTO> getEggRates() {
		List<EggPricePerLocationDTO> result = new ArrayList<EggPricePerLocationDTO>();
		List<EggPricePerLocationDTO> list = userDAO.getEggRates(false)!=null?userDAO.getEggRates(false):new ArrayList<EggPricePerLocationDTO>();
		if(list!=null && list.size()>0) {
			result = list;
		}
		return result;
	}
	
	@Transactional
	public List<EggPricePerLocationDTO> getYesterdayEggRates() {
		List<EggPricePerLocationDTO> result = new ArrayList<EggPricePerLocationDTO>();
		List<EggPricePerLocationDTO> list = userDAO.getEggRates(true)!=null?userDAO.getEggRates(true):new ArrayList<EggPricePerLocationDTO>();
		if(list!=null && list.size()>0) {
			result = list;
		}
		return result;
	}
	
	@Transactional
	public List<Location> getLocations() {
		List<Location> result = new ArrayList<Location>();
		List<Location> list = userDAO.getLocations()!=null?userDAO.getLocations():new ArrayList<Location>();
		if(list!=null && list.size()>0) {
			result = list;
		}
		return result;
	}
	@Transactional
	public User getUserData(Long userId) {
		return userDAO.getUserDataById(userId);
	}
	
	@Transactional
	public User getUserData(String mobileNumber) {
		return userDAO.getUserDataByMobile(mobileNumber);
	}
	
	public String generateOtp() {
		return new DecimalFormat("000000").format(new Random().nextInt(999999));
	}
	
	public boolean saveOtp(String mobileNumber, String otp) {
		// save and send msg
		return true;
	}
	
	public boolean validateOtp(Long userId, String otp) {
		return userDAO.validateOtp(userId, otp);
	}
	
	@Transactional
	public void updatePrices(UpdateEggRates updateEggRates) {
		userDAO.updatePrices(updateEggRates);
	}

}
