package com.niit.controllers;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.niit.dao.CartItemDAO;
import com.niit.dao.OrderDetailDAO;
import com.niit.dao.ProductDAO;
import com.niit.dao.SupplierDAO;
import com.niit.model.CartItem;
import com.niit.model.OrderDetail;
import com.niit.model.Product;
import com.niit.model.Supplier;

@Controller
public class Ordercontroller {
	@Autowired
	CartItemDAO cartItemDAO;
	@Autowired
	OrderDetailDAO orderDetailDAO;
	@Autowired
	SupplierDAO supplierDAO;
	@Autowired
	ProductDAO productDAO;
	
	@RequestMapping("/confirmOrder")
	public String confirmOrder(HttpSession session, Model m) {
		OrderDetail order = new OrderDetail();
		m.addAttribute("order", order);
		String username = (String)session.getAttribute("username");
		List<CartItem> listCartItems = cartItemDAO.listCartItems(username);		
		m.addAttribute("cartList", listCartItems);
		m.addAttribute("productDetails", this.getProductName(listCartItems));
		m.addAttribute("grandTotal", this.calculateGrandTotal(listCartItems));
		return "orderConfirm";
	}
	
	@RequestMapping("/thankYou")
	public String addOrderDetail(@ModelAttribute("order")OrderDetail orderDetail, HttpSession session, Model m) {
		String username = (String)session.getAttribute("username");
		List<CartItem> listCartItems = cartItemDAO.listCartItems(username);
		orderDetail.setCartId(listCartItems.get(0).getCartId());		
		orderDetail.setUsername(username);
		orderDetail.setTotalAmount(this.calculateGrandTotal(listCartItems));
		orderDetail.setOrderDate(new Date());
		orderDetail.setOrderStatus("Placed");
		int orderId = orderDetailDAO.addOrder(orderDetail);
		
		for(CartItem cartItem:listCartItems) {
			cartItem.setPaymentStatus("P");
			cartItemDAO.updateCartItem(cartItem);
			Product product = productDAO.viewProduct(cartItem.getProductId());
			if(product.getStock()>0)
				product.setStock((product.getStock()-1));
			else
				product.setStock(0);
			productDAO.updateProduct(product);
		}
		
		Map<String,String> productSupplierMap = new LinkedHashMap<String,String>();
		
		for(CartItem cartItem:listCartItems) {
			int productId = cartItem.getProductId();
			Product prod = productDAO.viewProduct(productId);
			Supplier supplier = supplierDAO.viewSupplier(prod.getSupplierId());
			productSupplierMap.put(prod.getProductName(),supplier.getSupplierName());
		}
		m.addAttribute("shippingAddress", orderDetail.getShippingAddress());
		m.addAttribute("orderId", orderId);
		m.addAttribute("productSupplierMap",productSupplierMap);
		m.addAttribute("nameOfUser",(String)session.getAttribute("nameOfUser"));
		return "thankYou";
	}
	
	@RequestMapping("/viewOrders")
	public String viewOrders(HttpSession session, Model m) {
		String username = (String)session.getAttribute("username");
		List<OrderDetail> orderList = orderDetailDAO.orderList(username);
		m.addAttribute("orderList", orderList);
		return "viewOrders";
	}
	
	public Map<Integer, String> getProductName(List<CartItem> cartList) {
		Map<Integer, String> productMap = new LinkedHashMap<Integer, String>();
		for(CartItem items: cartList) {
			productMap.put(items.getProductId(), productDAO.viewProduct(items.getProductId()).getProductName());
		}	
		return productMap;
	}
	
	private float calculateGrandTotal(List<CartItem> listCartItems) {
		float grandTotal=0.0f;
		for(CartItem cart:listCartItems) {
			grandTotal = grandTotal + cart.getSubTotal();
		}
		return grandTotal;
	}
}