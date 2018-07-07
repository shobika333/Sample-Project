package com.niit.daoimpl;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.niit.dao.CartItemDAO;
import com.niit.model.CartItem;

@Repository("cartItemDAO")
public class CartItemDAOImpl implements CartItemDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public boolean addCartItem(CartItem cartItem) {
		try {
			sessionFactory.getCurrentSession().save(cartItem);
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
	public boolean deleteCartItem(CartItem cartItem) {
		try {
			sessionFactory.getCurrentSession().delete(cartItem);
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
	public boolean updateCartItem(CartItem cartItem) {
		try {
			sessionFactory.getCurrentSession().update(cartItem);
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
	public CartItem getCartItem(int cartItemId) {
		try {
			return (CartItem) sessionFactory.getCurrentSession().get(CartItem.class, cartItemId);
		}
		catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<CartItem> listCartItems(String username) {
		try {
			String queryString = "from CartItem where username='"+username+"' and paymentStatus='NP'";
			return (List<CartItem>)sessionFactory.getCurrentSession().createQuery(queryString).list();
		} catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<CartItem> getCartItemByCartId(String username,int cartId) {
		try {
			String queryString = "from CartItem where username='"+username+"' and cartId="+cartId;
			return (List<CartItem>)sessionFactory.getCurrentSession().createQuery(queryString).list() ;
		} catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return null;
		}
	}
}