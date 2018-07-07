package com.niit.daoimpl;

import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.dao.OrderDetailDAO;
import com.niit.model.OrderDetail;

@Repository("orderDetailDAO")
public class OrderDetailDAOImpl implements OrderDetailDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public int addOrder(OrderDetail orderDetail) {
		try {
			int orderId = (int) sessionFactory.getCurrentSession().save(orderDetail);
			return orderId;
		}catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return 0;
		}
	}

	/*@Transactional
	@Override
	public boolean updateOrder(OrderDetail orderDetail) {
		try {
			sessionFactory.getCurrentSession().update(orderDetail);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean deleteOrder(OrderDetail orderDetail) {
		try {
			sessionFactory.getCurrentSession().delete(orderDetail);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return false;
		}
	}*/
	
	@Transactional
	@Override
	public OrderDetail getOrder(int orderId) {
		try {
			return (OrderDetail)sessionFactory.getCurrentSession().get(OrderDetail.class, orderId);
		}catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<OrderDetail> orderList(String username) {
		try {
			return (List<OrderDetail>)sessionFactory.getCurrentSession().createQuery("from OrderDetail where username='"+username+"'").list();
		}catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return null;
		}		
	}
}