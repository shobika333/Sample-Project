package com.niit.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.niit.dao.CategoryDAO;
import com.niit.dao.ProductDAO;
import com.niit.model.Category;
import com.niit.model.Product;
import com.niit.model.User;


@Controller
public class Pagecontroller {
	@Autowired
	CategoryDAO categoryDAO;
	@Autowired
	ProductDAO productDAO;	
	
	@RequestMapping("/")
	public String showHomePage(HttpSession session, Model m) {
		List<Category> listCategories = categoryDAO.listCategory();
		m.addAttribute("listCategories", listCategories);
		String username = (String)session.getAttribute("username");
		if(username!=null) {
			m.addAttribute("nameOfUser", (String)session.getAttribute("nameOfUser"));
			m.addAttribute("username",username);
		}
		else {
			m.addAttribute("nameOfUser", "Guest");
			m.addAttribute("username", "Guest");	
		}
		return "index";
	}
	
	@RequestMapping("/home")
	public String showHome(HttpSession session, Model m) {
		List<Category> listCategories = categoryDAO.listCategory();
		m.addAttribute("listCategories", listCategories);
		String username = (String)session.getAttribute("username");
		if(username!=null) {
			m.addAttribute("nameOfUser", (String)session.getAttribute("nameOfUser"));
			m.addAttribute("username",username);
		}
		else {
			m.addAttribute("nameOfUser", "Guest");
			m.addAttribute("username", "Guest");
		}
		return "index";
	}	
	@RequestMapping("/login")
	public String showLogin(Model m) {
		User user = new User();
		m.addAttribute("user", user);
		return "Login";
	}
	
	@RequestMapping("/register")
	public String showRegister(Model m) {
		User user = new User();
		m.addAttribute("user", user);
		return "Register";
	}
	
	@RequestMapping("/contactus")
	public String showContactUs() {
		return "contactUs";
	}
	
	@RequestMapping("/exploreProducts")
	public String showExploreProductsPage(HttpSession session, Model m) {
		String username = (String)session.getAttribute("username");
		if(username!=null) {
			List<Product> listProducts = productDAO.listProducts();
			m.addAttribute("listProducts", listProducts);
			m.addAttribute("catList", this.listCategories());
			return "viewProducts";
		}
		else
			return "exploreProducts";
	}
	
	public LinkedHashMap<Integer,String> listCategories() {
		LinkedHashMap<Integer,String> catList = new LinkedHashMap<Integer,String>();
		List<Category> listCategories = categoryDAO.listCategory();
		for(Category category: listCategories) {
			catList.put(category.getCategoryId(), category.getCategoryName());
		}
		return catList;
	}
}