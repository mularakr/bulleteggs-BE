package com.be.DAO.implementation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.hibernate.query.Query;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.be.DAO.interfaces.TokenDAO;
import com.be.entity.Token;


@Repository("tokenDAO")
public class TokenDAOImpl implements TokenDAO  {
	
	@Autowired
	SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.logger(TokenDAOImpl.class);
	
	public void saveUserEmail(String mobileNumber, Long userId) {
		Session session = null; 
		try
		{
			session = sessionFactory.getCurrentSession();
			Token t = new Token();
			t.setUserID(userId);
			t.setMobileNumber(mobileNumber);
			session.save(t); 
		}
		catch(Exception exception)
		{
			logger.error("Exception in saving UserEmail In Token Table :: {}" , exception.getMessage(), exception);
		}
		finally
		{
			session.flush();
		}
		
	}

	public boolean updateToken(String mobileNumber, String authenticationToken, String secretKey) {
		Session session = null;
		try 
		{
			session = sessionFactory.getCurrentSession();
			Query<Token> theQuery = null;		
			
			theQuery = session.createQuery("Update Token set authenticationToken = :authenticationToken , secretKey = :secretKey where mobileNumber =:mobileNumber ");
				
			theQuery.setParameter("authenticationToken", authenticationToken);
			theQuery.setParameter("mobileNumber", mobileNumber);
			theQuery.setParameter("secretKey", secretKey);

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

	public Long getTokenDetail(String mobileNumber) {
		Session session = null;
		try
		{
			session = sessionFactory.getCurrentSession();
			Query<Token> query = session.createQuery("from Token where mobileNumber =:mobileNumber");
			query.setParameter("mobileNumber", mobileNumber);
			
			List<Token> tokenDetails = query.list();
			
			if(tokenDetails.size() > 0)
			{
				return tokenDetails.get(0).getTokenID();
			}
			else
			{
				return 0l;
			}
			
		}
		catch(Exception exception)
		{
			logger.error("Exception while getting token ID :: {}" , exception.getMessage(), exception);		
		}
		finally
		{
			session.flush();
		}
		
		return 0l;
	}

	public Long tokenAuthentication(String token, Long userId) {
		Session session = null;
		
		try 
		{
			session = sessionFactory.getCurrentSession();
			
			List<Token> tokenDetails = session.createQuery("from Token where userID =:userID")
			.setParameter("userID", userId)
			.list();
			// query.setParameter("token", token);
			
			if(tokenDetails.size() > 0)
			{
				List<Token> tokens = (List<Token>) tokenDetails;
				if(tokens.stream().anyMatch(tok -> tok.getAuthenticationToken().equals(token)))
					return tokenDetails.get(0).getTokenID();
				else {
					return 0l;
				}
			}
			else
			{
				return 0l;
			}

		}
		catch(Exception exception)
		{
			logger.error("Exception while Authenticating token :: {}", exception.getMessage(), exception);
			return 0l;
		}
		finally
		{
			session.flush();
		}
		
		
	}

}
