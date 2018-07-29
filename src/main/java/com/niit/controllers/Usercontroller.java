package com.niit.controllers;

import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.niit.dao.CategoryDAO;
import com.niit.dao.UserDAO;
import com.niit.model.Category;
import com.niit.model.User;

@Controller
public class Usercontroller {
	@Autowired
	UserDAO userDAO;
	@Autowired
	CategoryDAO categoryDAO;
	
	@RequestMapping("/viewUsersList")
	public String listUsers(Model m) {
		List<User> userList = userDAO.listUser();
		m.addAttribute("userList", userList);
		return "viewUsersList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/login_success")
	public String showSuccessPage(HttpSession session, Model m) {
		String page="";
		boolean loggedIn = false;
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		String username = authentication.getName();
		User userFromDB;
		
		Collection<GrantedAuthority> rolesList = (Collection<GrantedAuthority>)securityContext.getAuthentication().getAuthorities();
		
		for(GrantedAuthority role: rolesList) {
			session.setAttribute("role", role);
			if(role.getAuthority().equals("ROLE_ADMIN")) {
				loggedIn = true;
				page = "adminHome";
				userFromDB = userDAO.viewUserDetailByUsername(username);
				session.setAttribute("nameOfUser", userFromDB.getName());
				session.setAttribute("username", username);
				session.setAttribute("loggedIn", loggedIn);
				session.setAttribute("userType", "admin");
			}
			else {
				loggedIn = true;
				page = "index";
				userFromDB = userDAO.viewUserDetailByUsername(username);
				session.setAttribute("nameOfUser", userFromDB.getName());
				session.setAttribute("username", username);
				session.setAttribute("loggedIn", loggedIn);
				session.setAttribute("userType", "user");
				List<Category> listCategories = categoryDAO.listCategory();
				m.addAttribute("listCategories", listCategories);
			}				
		}		
		return page;
	}
	
	@RequestMapping(value="/editUser/{username}", method=RequestMethod.GET)
	public String showUpdateUserPage(@PathVariable("username")String username,Model m) {
		User user = userDAO.viewUserDetailByUsername(username);
		m.addAttribute("user", user);
		return "editUserDetails";
	}
	
	@RequestMapping(value="/editUserDetails", method=RequestMethod.POST)
	public String updateUserDetails(@ModelAttribute("user")User user, Model m, HttpSession session) {
		System.out.println("Inside update User Details method");
		System.out.println("User Name from the form is "+user.getUsername());
		userDAO.updateUserDetail(user);		
		
		List<User> userList = userDAO.listUser();
		m.addAttribute("userList", userList);
		String userType = (String)session.getAttribute("userType");
		if(userType.equals("admin"))
			return "viewUsersList";
		else {
			List<Category> listCategories = categoryDAO.listCategory();
			m.addAttribute("listCategories", listCategories);
			return "index";
		}
	}
	
	@RequestMapping(value="/addUserDetail",method=RequestMethod.POST)
	public String addUser(@ModelAttribute("user")User user, Model m) {
		System.out.println("Name of the user from the form: "+user.getName());
		System.out.println("Name of the user from the form: "+user.getUsername());
		String message;
		User userCheckByUsername, userCheckByEmail = null;
		userCheckByUsername = userDAO.viewUserDetailByUsername(user.getUsername());
		if(userCheckByUsername==null)
			System.out.println("No user returned for username "+user.getUsername());
		userCheckByEmail = userDAO.viewUserDetailByEmail(user.getEmailId());
		if(userCheckByUsername!=null && userCheckByEmail!=null) {
			message = "Both username and email are already registered";
			System.out.println(message);
			m.addAttribute("message", message);
			m.addAttribute("user", user);
			return "Register";			
		} else if(userCheckByUsername==null && userCheckByEmail!=null){
			message = "Email are already registered. Please login";
			System.out.println(message);
			m.addAttribute("message", message);
			m.addAttribute("user", user);
			return "Register";
		}else if(userCheckByUsername!=null && userCheckByEmail==null){
			message = "Username already exists. Please try with a different username";
			System.out.println(message);
			m.addAttribute("message", message);
			m.addAttribute("user", user);
			return "Register";
		} else {
			user.setEnabled(true);
			user.setRole("ROLE_USER");
			userDAO.addUserDetail(user);
			System.out.println("New User Added!");
			m.addAttribute("user", user);
			return "Login";
		}		
	}
	
	@RequestMapping(value="/deleteUser/{username}", method=RequestMethod.GET)
	public String deleteUserDetails(@PathVariable("username")String username, Model m) {
		User user = userDAO.viewUserDetailByUsername(username);
		userDAO.deleteUserDetail(user);
		List<User> userList = userDAO.listUser();
		m.addAttribute("userList", userList);
		return "viewUsersList";
	}
}