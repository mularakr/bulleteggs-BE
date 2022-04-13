package com.be.DAO.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.be.DAO.interfaces.UserDAO;
import com.be.entity.EggPricePerLocationDTO;
import com.be.entity.EggRate;
import com.be.entity.Location;
import com.be.entity.SellerCategory;
import com.be.entity.UpdateEggRates;
import com.be.entity.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LoggerFactory.logger(UserDAOImpl.class);
	// Autowired SessionFactory Object So that we can get session object used for
	// interaction with Database.
	@Autowired
	private SessionFactory sessionFactory;

	/*
	 * Register user Details.
	 */
	public Long saveUserDetail(User userDetail) {

		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();

			return (Long) session.save(userDetail);
		} catch (Exception exception) {
			// logger.error("Excecption while saving user Details : {}" ,
			// exception.getMessage(), exception);
			return 0l;
		} finally {
			session.flush();
		}
	}

	public Long userLogin(String mobileNumber, String password) {

		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from User where mobileNumber= :mobileNumber and password= :password");
			query.setParameter("mobileNumber", mobileNumber);
			query.setParameter("password", password);
			List<User> list = query.list();

			int size = list.size();
			if (size == 1) {
				return list.get(0).getUserID();
			}
			return 0l;
		} catch (Exception exception) {
			logger.error("Exception while saving user Details : {}" , exception.getMessage(), exception);
			return 0l;
		} finally {
			session.flush();
		}

	}

	public List<User> getUserData() {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();

			Query<User> query = session.createQuery("from User");
			List<User> list = query.list();

			if (list.size() > 0) {
				return list;
			} else {
				return null;
			}

		} catch (Exception exception) {
			// logger.error("Excecption while getting user Details : {}" +
			// exception.getMessage(), exception);
			return null;
		} finally {
			session.flush();
		}

	}

	public User getUserDataById(Long userId) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();

			Query<User> query = session.createQuery("from User where userID = :userId");
			query.setParameter("userId", userId);
			List<User> list = query.getResultList();

			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			// logger.error("Excecption while getting user Details : {}" +
			// exception.getMessage(), exception);
			return null;
		} finally {
			session.flush();
		}

	}

	public User getUserDataByMobile(String mobileNumber) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();

			Query<User> query = session.createQuery("from User where mobileNumber = :mobileNumber");
			query.setParameter("mobileNumber", mobileNumber);
			List<User> list = query.getResultList();

			if (list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			// logger.error("Excecption while getting user Details : {}" +
			// exception.getMessage(), exception);
			return null;
		} finally {
			session.flush();
		}

	}

	public List<User> getUserData(Long userId) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from User where userID=:userId");
			query.setParameter("userId", userId);
			List<User> list = query.list();

			if (list.size() > 0) {
				return list;
			} else {
				return null;
			}

		} catch (Exception exception) {
			// logger.error("Excecption while getting user Details : {}" ,
			// exception.getMessage(), exception);
			return null;
		} finally {
			session.flush();
		}

	}

	@Transactional
	public synchronized List<EggPricePerLocationDTO> getEggRates(boolean prev) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query query = null;
			if (prev == true) {
				query = session.createNativeQuery(
						"select cityName, currentTimestamp, pricePerEgg, sellerCategory from tbl_location tl \r\n"
								+ "		inner join tbl_egg_rates ter on tl.id = ter.location_id \r\n"
								+ "        where ter.currentTimestamp =(select distinct currentTimestamp from tbl_egg_rates iter order by currentTimestamp desc limit 1,1)");
			} else {
				query = session.createNativeQuery(
						"select cityName, currentTimestamp, pricePerEgg, sellerCategory from tbl_location tl \r\n"
								+ "		inner join tbl_egg_rates ter on tl.id = ter.location_id \r\n"
								+ "        where ter.currentTimestamp =(select currentTimestamp from tbl_egg_rates iter order by currentTimestamp desc limit 1)");
			}
			List<EggPricePerLocationDTO> list = query.list();
			
			if (list.size() > 0) {
				return list;
			} else {
				return null;
			}

		} catch (Exception exception) {
			logger.error("Excecption while getting egg Details : {}" , exception.getMessage(), exception);
			return null;
		} finally {
			session.flush();
		}

	}

	@Transactional
	public List<Location> getLocations() {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from Location");

			List<Location> list = query.list();

			if (list.size() > 0) {
				return list;
			} else {
				return null;
			}

		} catch (Exception exception) {
			// logger.error("Excecption while getting egg Details : {}" ,
			// exception.getMessage(), exception);
			return null;
		} finally {
			session.flush();
		}

	}

	@Transactional
	public boolean validateOtp(Long userId, String otp) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query q = session.createQuery("from Otp o where o.user.id=:1 and otp=:2");
			q.setParameter(1, userId);
			q.setParameter(2, otp);
			if (q.list() == null || q.list().isEmpty()) {
				return false;
			}

		} catch (Exception e) {
			logger.error("error while validating otp {}", e.getMessage(), e);
		}
		return true;
	}

	@Transactional
	public void updatePrices(UpdateEggRates updateEggRates) {
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			EggRate err = new EggRate();
			err.location = updateEggRates.location;

			Query query = session.createQuery(
					"from EggRate er where er.location.id = :locationId and er.currentTimestamp = :currentTimestamp and er.sellerCategory = :sellerCategory");
			query.setParameter("locationId", updateEggRates.location.id);
			query.setParameter("currentTimestamp", updateEggRates.date);
			query.setParameter("sellerCategory", SellerCategory.values()[updateEggRates.sellerCategory]);
			List<EggRate> list = query.list();

			
			if (list.size() > 0) {
				//session.close();
				//session = sessionFactory.getCurrentSession();
				Query theQuery = session.createQuery(
						"Update EggRate err set err.pricePerEgg = :pricePerEgg where err.location.id =:locationId and err.currentTimestamp = :currentTimestamp and err.sellerCategory = :sellerCategory ");

				theQuery.setParameter("pricePerEgg", updateEggRates.ppEgg);
				theQuery.setParameter("sellerCategory", SellerCategory.values()[updateEggRates.sellerCategory]);
				theQuery.setParameter("locationId", updateEggRates.location.id);
				theQuery.setParameter("currentTimestamp", updateEggRates.date);

				theQuery.executeUpdate();
			} else {
				err.sellerCategory = SellerCategory.values()[updateEggRates.sellerCategory];
				err.pricePerEgg = updateEggRates.ppEgg;
				err.currentTimestamp = updateEggRates.date;
				session.save(err);
			}

		} catch (Exception e) {
			logger.error("error while updating eggPrices {}", e.getMessage(), e);
		}finally {
			session.flush();
		}
	}

}
