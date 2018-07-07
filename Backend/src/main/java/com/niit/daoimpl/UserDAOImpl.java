package com.niit.daoimpl;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.dao.UserDAO;
import com.niit.model.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public boolean addUserDetail(User user) {
		try {
			sessionFactory.getCurrentSession().save(user);
			return true;
		}
		catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean deleteUserDetail(User user) {
		try {
			sessionFactory.getCurrentSession().delete(user);
			return true;
		}
		catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return false;
		}
	}
	@Transactional
	@Override
	public boolean updateUserDetail(User user) {
		try {
			sessionFactory.getCurrentSession().update(user);
			return true;
		}
		catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return false;
		}
	}
	
	@Transactional
	@Override
	public User viewUserDetailByEmail(String emailId) {
		try {
			return (User) sessionFactory.getCurrentSession().createQuery("from User where emailID='"+emailId+"'").list();
		}catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return null;
		}		
	}

	@Transactional
	@Override
	public User viewUserDetailByUsername(String username) {
		try {
			return (User) sessionFactory.getCurrentSession().createQuery("from User where username='"+username+"'").list().get(0);
		}catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return null;
		}		
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<User> listUser() {
		try {
			return (List<User>) sessionFactory.getCurrentSession().createQuery("from User").list();
		}catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return null;
		}		
	}	
}
