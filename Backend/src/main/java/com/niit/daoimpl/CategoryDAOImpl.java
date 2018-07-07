package com.niit.daoimpl;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.niit.dao.CategoryDAO;
import com.niit.model.Category;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {
	@Autowired
	SessionFactory sessionFactory;	
	
	@Transactional
	@Override
	public boolean addCategory(Category category) 
	{	
		try {
			sessionFactory.getCurrentSession().save(category);
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
	public Category getCategory(int categoryId) {
		try{
			return (Category)sessionFactory.getCurrentSession().get(Category.class,categoryId);
		}catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n ================================= \n"+e);
			return null;
		}		
	}

	@Transactional
	@Override
	public boolean deleteCategory(Category category) {
		try {
			sessionFactory.getCurrentSession().delete(category);
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
	public boolean updateCategory(Category category) {
		try {
			sessionFactory.getCurrentSession().update(category);
			return true;
		}
		catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Category> listCategory() {
		try{
			return (List<Category>)sessionFactory.getCurrentSession().createQuery("from Category").list();
		}catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n ================================= \n"+e);
			return null;
		}
	}	
}
