package com.niit.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.niit.dao.CategoryDAO;
import com.niit.model.Category;

@Controller
public class Categorycontroller {
	@Autowired
	CategoryDAO categoryDAO;
	
	public boolean flag;
	
	@RequestMapping("/addCategory")
	public String showCategory(Model m) {
		List<Category> listCategories = categoryDAO.listCategory();
		m.addAttribute("listCategories", listCategories);
		return "addCategory";
	}
	
	@RequestMapping("/updateCategoryDetails")
	public String showCategoryforUpdate(Model m) {
		List<Category> listCategories = categoryDAO.listCategory();
		m.addAttribute("listCategories", listCategories);
		return "updateCategoryDetails";
	}
	
	@RequestMapping("/editCategory/{categoryId}")
	public String updateCategory(@PathVariable("categoryId") int categoryId, Model m) {
		Category category = categoryDAO.getCategory(categoryId);
				
		List<Category> listCategories = categoryDAO.listCategory();
		m.addAttribute("listCategories", listCategories);
		m.addAttribute("categoryInfo", category);
		return "updateCategory";
	}
	
	@RequestMapping(value="/insertCategory",method=RequestMethod.POST)
	public String insertCategoryInDB(@RequestParam("catname") String categoryName,@RequestParam("catDesc") String categoryDesc,  Model m) {
		Category category = new Category();
		category.setCategoryName(categoryName);
		category.setCategoryDesc(categoryDesc);
		categoryDAO.addCategory(category);
		
		List<Category> listCategories = categoryDAO.listCategory();
		m.addAttribute("listCategories", listCategories);
		
		return "addCategory";
	}
	
	@RequestMapping(value="/updateCategory",method=RequestMethod.POST)
	public String updateCategoryInDB(@RequestParam("catId") int categoryId,@RequestParam("catname") String categoryName,@RequestParam("catDesc") String categoryDesc,  Model m) {
		Category category = categoryDAO.getCategory(categoryId);
		category.setCategoryName(categoryName);
		category.setCategoryDesc(categoryDesc);
		categoryDAO.updateCategory(category);
		
		List<Category> listCategories = categoryDAO.listCategory();
		m.addAttribute("listCategories", listCategories);
		
		return "addCategory";
	}
	
	@RequestMapping("/deleteCategory/{categoryId}")
	public String deleteCategoryInDB(@PathVariable("categoryId") int categoryId,  Model m) {
		Category category = categoryDAO.getCategory(categoryId);
		categoryDAO.deleteCategory(category);
		
		List<Category> listCategories = categoryDAO.listCategory();
		m.addAttribute("listCategories", listCategories);
		
		return "updateCategoryDetails";
	}	
}