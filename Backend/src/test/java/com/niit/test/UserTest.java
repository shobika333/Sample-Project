package com.niit.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.niit.dao.UserDAO;
import com.niit.model.User;

public class UserTest {
	
	static UserDAO userDAO;
	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void executeFirst() {
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		userDAO=(UserDAO)context.getBean("userDAO");
	}
	@Ignore
	@Test
	public void addUserDetailTest() {
		User user = new User();
		user.setUsername("U1002");
		user.setName("Sansa");
		user.setPassword("pass123");
		user.setMobileNumber("9943952979");
		user.setEmailId("sansa@home.com");
		user.setAddress("46, Winterfell - HYD12Z");
		user.setEnabled(true);
		user.setRole("admin");
		assertTrue("Problem in Product Insertion",userDAO.addUserDetail(user));
	}
	
	@Ignore
	@Test
	public void deleteUserDetailTest() {
		User user = userDAO.viewUserDetailByEmail("sansa@winterfell.com");
		assertTrue("Problem in Product Insertion",userDAO.deleteUserDetail(user));
	}
	
	@Ignore
	@Test
	public void viewUserDetailByEmailTest() {
		assertNotNull("Problem in get Product",userDAO.viewUserDetailByEmail("arya@winterfell.com"));
		User user = userDAO.viewUserDetailByEmail("arya@winterfell.com");
		System.out.println("User Name is "+user.getUsername());
		System.out.println("Name is "+user.getName());
		System.out.println("Password is "+user.getPassword());
		System.out.println("Mobile number is "+user.getMobileNo());
		System.out.println("Address is "+user.getAddress());
	}	
	
	@Ignore
	@Test
	public void viewUserDetailByUsernameTest() {
		assertNotNull("Problem in get Product",userDAO.viewUserDetailByUsername("U1001"));
		User user = userDAO.viewUserDetailByUsername("U1001");
		System.out.println("User Name is "+user.getUsername());
		System.out.println("Name is "+user.getName());
		System.out.println("Password is "+user.getPassword());
		System.out.println("Mobile number is "+user.getMobileNo());
		System.out.println("Email ID is "+user.getEmailId());
		System.out.println("Address is "+user.getAddress());
	}
	
	@Ignore
	@Test
	public void updateUserDetailTest() {
		User user = userDAO.viewUserDetailByUsername("U1002");
		//user.setAddress("22, Hate Hound, Roam Around, Needle - STARK");
		user.setEmailId("sansa@winterfell.com");
		assertTrue("Problem in Product Insertion",userDAO.updateUserDetail(user));
	}
	@Ignore	
	@Test
	public void listUser(){
		assertNotNull("Issue in listing user details..",userDAO.listUser());
		List<User> listUser = userDAO.listUser();
		System.out.println("User Name\t Name \t\t Mobile Number\t\t Email ID\t\t\t Address");
		for(User user:listUser) {
			System.out.print(user.getUsername()+"\t\t");
			System.out.print(user.getName()+"\t\t");
			System.out.print(user.getMobileNo()+"\t\t");
			System.out.print(user.getEmailId()+"\t\t");
			System.out.println(user.getAddress());
		}
	}
}