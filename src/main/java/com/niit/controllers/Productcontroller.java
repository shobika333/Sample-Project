package com.niit.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.niit.dao.CategoryDAO;
import com.niit.dao.ProductDAO;
import com.niit.model.CartItem;
import com.niit.model.Category;
import com.niit.model.Product;

@Controller
public class Productcontroller {
	@Autowired
	ProductDAO productDAO;
	@Autowired
	CategoryDAO categoryDAO;
	
	public boolean flag;
	
	@RequestMapping("/addProduct")
	public String showProduct(Model m) {
		Product product = new Product();
		List<Product> listProducts = productDAO.listProducts();
		m.addAttribute(product);
		m.addAttribute("catList", this.listCategories());
		m.addAttribute("listProducts", listProducts);
		return "addProduct";
	}
	
	@RequestMapping("/updateProduct")
	public String showProductUpdatePage(Model m) {
		Product product = new Product();
		List<Product> listProducts = productDAO.listProducts();
		m.addAttribute(product);
		m.addAttribute("listProducts", listProducts);
		return "updateDeleteProduct";
	}
	
	@RequestMapping(value="/insertProduct", method=RequestMethod.POST)
	public String addProduct(@ModelAttribute("product")Product product, Model m,@RequestParam("pimage") MultipartFile filedet, BindingResult result) {
		System.out.println("Insert Product method");
		Product prod = new Product();
		m.addAttribute(prod);		
		productDAO.addProduct(product);
		
		boolean uploadResult = saveImage(filedet, product.getProductId());
		if(uploadResult)
			System.out.println("Image uploaded successfully");
		else
			System.out.println("Error in image upload");
		
		List<Product> listProducts = productDAO.listProducts();
		m.addAttribute("listProducts", listProducts);
		m.addAttribute("catList", this.listCategories());
		return "addProduct";
	}
	
	public LinkedHashMap<Integer,String> listCategories() {
		LinkedHashMap<Integer,String> catList = new LinkedHashMap<Integer,String>();
		List<Category> listCategories = categoryDAO.listCategory();
		for(Category category: listCategories) {
			catList.put(category.getCategoryId(), category.getCategoryName());
		}
		return catList;
	}
	
	@RequestMapping("/editProduct/{productId}")
	public String updateProduct(@PathVariable("productId") int productId, Model m) {
		System.out.println("Product ID from the URL: "+productId);
		Product prod = productDAO.viewProduct(productId);
		System.out.println("Product ID from Product Object in DB: "+prod.getProductId());
		m.addAttribute("product", prod);		
		List<Product> listProducts = productDAO.listProducts();
		m.addAttribute("listProducts", listProducts);
		m.addAttribute("catList", this.listCategories());
		return "editProduct";
	}
	
	@RequestMapping(value="/editProduct",method=RequestMethod.POST)
	public String updateProductInDB(@ModelAttribute("product")Product product, Model m,@RequestParam("pimage") MultipartFile filedet, BindingResult result) {
		System.out.println("Update Product in DB method");
		productDAO.updateProduct(product);		
		boolean uploadResult = saveImage(filedet, product.getProductId());
		if(uploadResult)
			System.out.println("Image uploaded successfully");
		else
			System.out.println("Error in image upload");
		List<Product> listProducts = productDAO.listProducts();
		m.addAttribute("listProducts", listProducts);
		return "updateDeleteProduct";
	}	
	
	public boolean saveImage(MultipartFile filedet, int productId) {
		System.out.println("Inside save image method");
		boolean flag = false;
		String imagePath = "D:\\Ecomproject-master\\Frontend\\src\\main\\webapp\\resources\\images\\product\\";
		imagePath = imagePath + String.valueOf(productId) + ".jpg";
		System.out.println("Image Path: " + imagePath);
		File image = new File(imagePath);
		if (!filedet.isEmpty()) {
			try {
				System.out.println("Inside try block");
				byte[] fileBuffer = filedet.getBytes();
				FileOutputStream fos = new FileOutputStream(image);
				BufferedOutputStream bs = new BufferedOutputStream(fos);
				bs.write(fileBuffer);
				bs.close();
				flag = true;
			} catch (Exception e) {
				System.out.println("There is an exception" + e);
				e.printStackTrace();
			}
		} else {
			System.out.println("Inside else block");
		}
		return flag;
	}
	
	@RequestMapping(value="/viewProducts", method=RequestMethod.GET)
	public String viewProducts(Model m) {
		List<Product> listProducts = productDAO.listProducts();
		m.addAttribute("listProducts", listProducts);
		m.addAttribute("catList", this.listCategories());
		return "viewProducts";
	}
	
	@RequestMapping(value="/viewProductDetail/{productId}",method=RequestMethod.GET)
	public String viewProductDetail(@PathVariable("productId") int productId, Model m) {
		Product product = productDAO.viewProduct(productId);
		CartItem cartItem = new CartItem();
		m.addAttribute("cart", cartItem);
		m.addAttribute("product", product);
		LinkedHashMap<Integer,String> quantityMap = new LinkedHashMap<Integer,String>();
		for(int i=1;i<=product.getStock();i++)
			quantityMap.put(i,String.valueOf(i));
		m.addAttribute("quantityValues", quantityMap);
		return "viewProductDetails";
	}
	
	@RequestMapping("/deleteProduct/{productId}")
	public String deleteProduct(@PathVariable("productId") int productId, Model m) {
		System.out.println("Product ID from the URL: "+productId);
		Product prod = productDAO.viewProduct(productId);
		System.out.println("Product deleted is: "+prod.getProductName());
		productDAO.deleteProduct(prod);				
		List<Product> listProducts = productDAO.listProducts();
		m.addAttribute("listProducts", listProducts);
		return "updateDeleteProduct";
	}	
}