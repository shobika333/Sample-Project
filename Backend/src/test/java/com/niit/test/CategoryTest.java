package com.niit.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.niit.dao.CategoryDAO;
import com.niit.model.Category;

public class CategoryTest {
	static CategoryDAO categoryDAO;
	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void executeFirst() {
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		categoryDAO=(CategoryDAO)context.getBean("categoryDAO");
	}
	
	@Ignore
	@Test
	public void addCategoryTest() {
		Category category=new Category();
		category.setCategoryName("Gadgets");
		category.setCategoryDesc("Latest gadgets");
		assertTrue("Problem in Category Insertion",categoryDAO.addCategory(category));
	}
	
	@Ignore
	@Test
	public void getCategoryTest() {
		assertNotNull("Problem in get Category",categoryDAO.getCategory(2));
	}
	
	@Ignore
	@Test
	public void deleteCategoryTest() {
		Category category = categoryDAO.getCategory(4);		
		assertTrue("Problem in Category Deletion",categoryDAO.deleteCategory(category));		
	}
	
	@Ignore
	@Test
	public void updateCategoryTest() {
		Category category = categoryDAO.getCategory(5);
		category.setCategoryName("Electronics");
		assertTrue("Problem in Category updation",categoryDAO.updateCategory(category));		
	}
	
	@Test
	public void listCategoryTest() {
		assertNotNull("Problem in listing all Category",categoryDAO.listCategory());
		System.out.println("Category ID   Category Name \t\t Category Desc");
		for(Category category:categoryDAO.listCategory()) {
			System.out.print(category.getCategoryId()+"\t\t");
			System.out.print(category.getCategoryName()+"\t\t\t");
			System.out.println(category.getCategoryDesc());
		}
	}
}
