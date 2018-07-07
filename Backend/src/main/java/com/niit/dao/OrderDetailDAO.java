package com.niit.dao;

import java.util.List;

import com.niit.model.OrderDetail;

public interface OrderDetailDAO {
	public int addOrder(OrderDetail orderDetail);
	//public boolean updateOrder(OrderDetail orderDetail);
	//public boolean deleteOrder(OrderDetail orderDetail);
	public OrderDetail getOrder(int cartId);
	public List<OrderDetail> orderList(String username);
	
}

