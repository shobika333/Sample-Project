package com.niit.test;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.niit.dao.OrderDetailDAO;
import com.niit.model.OrderDetail;

public class OrderDetailTest {
	static OrderDetailDAO orderDetailDAO;
	
	@SuppressWarnings("resource")
	@BeforeClass	
	public static void executeFirst() {
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		orderDetailDAO = (OrderDetailDAO)context.getBean("orderDetailDAO");
	}
	
	@Ignore
	@Test
	public void addOrderDetailTest() {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCartId(1005);		
		orderDetail.setUsername("koushika01");
		orderDetail.setTotalAmount(10000.0f);
		orderDetail.setOrderDate(new Date());		
		assertNotEquals(0,orderDetailDAO.addOrder(orderDetail));
	}
	
	@Ignore
	@Test
	public void getOrderDetailTest() {
		assertNotNull("Error fetching the order details",orderDetailDAO.getOrder(1));
		OrderDetail orderDetail = orderDetailDAO.getOrder(1);
		System.out.println("Order Details: ");
		System.out.println(orderDetail.getUsername()+"::::"+orderDetail.getShippingAddress());
	}	
	
	@Test
	public void listOrderDetailsTest() {
		List<OrderDetail> listOrderDetails = orderDetailDAO.orderList("U1001");
		assertNotNull("Problem in retrieving order list",orderDetailDAO.orderList("U1001"));
		System.out.println("Order ID\tOrder Date\t\t\tTotal Amount");
		for(OrderDetail order:listOrderDetails) {
			System.out.print(order.getOrderId()+"\t\t");
			System.out.print(order.getOrderDate()+"\t\t");
			System.out.println(order.getTotalAmount());
		}
	}
}