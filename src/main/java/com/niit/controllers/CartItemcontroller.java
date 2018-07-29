package com.niit.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.niit.dao.CartItemDAO;
import com.niit.dao.ProductDAO;
import com.niit.model.CartItem;
import com.niit.model.Product;

@Controller
public class CartItemcontroller {
	@Autowired
	CartItemDAO cartItemDAO;
	@Autowired
	ProductDAO productDAO;
	
	@RequestMapping("/viewCart")
	public String showCartPage(Model m, HttpSession session) {
		String username = (String)session.getAttribute("username");
		List<CartItem> cartList = cartItemDAO.listCartItems(username);		
		m.addAttribute("productDetails", this.getProductName(cartList));
		m.addAttribute("cartList",cartList);
		return "cart";
	}	
	
	@RequestMapping("/addToCart/{productId}")
	public String addCartItem(@PathVariable("productId")int productId,@RequestParam("quantity")int quantity, Model m, HttpSession session) {
		System.out.println("Quantity Selected: "+quantity);
		Product product = productDAO.viewProduct(productId);
		String username = (String)session.getAttribute("username");
		CartItem cartItem = new CartItem();		
		cartItem.setPaymentStatus("NP");
		cartItem.setProductId(productId);
		cartItem.setQuantity(quantity);
		cartItem.setUsername(username);
		cartItem.setSubTotal(quantity*product.getPrice());
		cartItem.setCartId(1001);
		cartItemDAO.addCartItem(cartItem);
		List<CartItem> cartList = cartItemDAO.listCartItems(username);		
		m.addAttribute("productDetails", this.getProductName(cartList));
		m.addAttribute("cartList",cartList);
		return "cart";
	}
	
	@RequestMapping("/editCart/{cartItemId}")
	public String editCartItems(@PathVariable("cartItemId")int cartItemId, HttpSession session, Model m, @ModelAttribute("cartItem")CartItem cartItem) {
		cartItem = cartItemDAO.getCartItem(cartItemId);
		System.out.println("Product ID: "+cartItem.getProductId());
		System.out.println("Username: "+cartItem.getUsername());
		Product product = productDAO.viewProduct(cartItem.getProductId());
		m.addAttribute("cartItem", cartItem);
		m.addAttribute("product", product);
		LinkedHashMap<Integer,Integer> quantityMap = new LinkedHashMap<Integer,Integer>();
		for(int i=1;i<=product.getStock();i++)
			quantityMap.put(i,i);
		m.addAttribute("quantityValues", quantityMap);
		return "editCartItem";
	}
	
	@RequestMapping("/updateCart")
	public String updateCartItem(@ModelAttribute("cartItem")CartItem cartItem, HttpSession session, Model m) {
		System.out.println(cartItem.getCartItemId()+"::"+cartItem.getProductId()+"::"+cartItem.getQuantity()+"::"+cartItem.getSubTotal());
		cartItemDAO.updateCartItem(cartItem);
		String username = (String)session.getAttribute("username");
		List<CartItem> cartList = cartItemDAO.listCartItems(username);		
		m.addAttribute("productDetails", this.getProductName(cartList));
		m.addAttribute("cartList",cartList);
		return "cart";
	}
	
	@RequestMapping("/deleteCart/{cartItemId}")
	public String deleteCartItems(@PathVariable("cartItemId")int cartItemId, HttpSession session, Model m) {
		String username = (String)session.getAttribute("username");
		CartItem cartItem = cartItemDAO.getCartItem(cartItemId);
		cartItemDAO.deleteCartItem(cartItem);
		List<CartItem> cartList = cartItemDAO.listCartItems(username);		
		m.addAttribute("productDetails", this.getProductName(cartList));
		m.addAttribute("cartList",cartList);
		return "cart";
	}
	
	public Map<Integer, String> getProductName(List<CartItem> cartList) {
		Map<Integer, String> productMap = new LinkedHashMap<Integer, String>();
		for(CartItem items: cartList) {
			productMap.put(items.getProductId(), productDAO.viewProduct(items.getProductId()).getProductName());
		}	
		return productMap;
	}
}