package com.be.DAO.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.be.entity.Profile;

@Repository
public class ProfileDAOImpl {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void saveProfile(Profile profile) {
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			session.save(profile.getAddress());
			session.save(profile);
			
		}
		catch(Exception exception)
		{
			System.out.println("Excecption while saving profile : " + exception.getMessage());
		}
		finally
		{
			session.flush();
		}
		
	}
	
	@Transactional
	public List<Profile> getProfiles(Long userId) {
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			
			Query query = session.createQuery("from Profile pr where pr.user.id =:userId");
			query.setParameter("userId", userId);
			List<Profile> list = query.list();
			
			
			if(list.size() > 0)
			{
				return list;
			}
			else
			{
				return null;
			}
			
		}
		catch(Exception exception)
		{
			System.out.println("Excecption while fetching profile Details : " + exception.getMessage());
			return null;
		}
		finally
		{
			session.flush();
		}
		
	}
}
