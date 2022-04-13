package com.be.DAO.implementation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.be.entity.Post;
import com.be.entity.Token;

@Repository
public class PostDAOImpl {
	private static final Logger logger = LoggerFactory.logger(UserDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	public List<Post> getPosts() {
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			
			Query query = session.createQuery("from Post");
			List<Post> list = query.list();
			
			
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
			logger.error("Excecption while fetching post Details : {}" , exception.getMessage(), exception);
			return null;
		}
		finally
		{
			session.flush();
		}
		
	}
	
	public List<Post> getPosts(Long userId) {
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			
			Query query = session.createQuery("from Post p where p.user.id = :userId");
			query.setParameter("userId", userId);
			List<Post> list = query.list();
			
			
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
			logger.error("Excecption while fetching post Details : {}" , exception.getMessage(), exception);
			return null;
		}
		finally
		{
			session.flush();
		}
		
	}
	
	public void savePost(Post post) {
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			session.save(post);
			
		}
		catch(Exception exception)
		{
			logger.error("Excecption while saving post : {}", exception.getMessage(), exception);
		}
		finally
		{
			session.flush();
		}
		
	}
	
	public void deletePost(Long postId) {
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from Post p where p.id =:postId");
			query.setParameter("postId", postId);
			List<Post> list = query.list();
			
			
			if(list.size() > 0)
			{
				Post post = list.get(0);
				session.delete(post);
			}
			
		}
		catch(Exception exception)
		{
			logger.error("Excecption while saving post : {}" , exception.getMessage(), exception);
		}
		finally
		{
			session.flush();
		}
		
	}

	public boolean markAsSold(Long postId) {
		Session session = null;
		try 
		{
			session = sessionFactory.getCurrentSession();
			Query<Token> theQuery = null;		
			
			theQuery = session.createQuery("Update Post set sold = true where id = :id");
				
			theQuery.setParameter("id", postId);
			int result = theQuery.executeUpdate();
					
			if(result == 1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception exception)
		{
			logger.error("Error while updating token :: {} " , exception.getMessage(), exception);
			return false;
		}
		finally
		{
			session.flush();
		}			
		
	}
}
