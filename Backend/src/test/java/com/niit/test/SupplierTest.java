package com.niit.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.niit.dao.SupplierDAO;
import com.niit.model.Supplier;

public class SupplierTest {
	static SupplierDAO supplierDAO;
	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void executeFirst()
	{
		System.out.println("Before Class Method in JUnit Class");
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		System.out.println("All config annotated classes are loaded");
		supplierDAO=(SupplierDAO)context.getBean("supplierDAO");
	}
	@Ignore
	@Test
	public void addSupplierTest() {
		Supplier supplier = new Supplier();
		supplier.setSupplierId(104);
		supplier.setSupplierName("Theon Greyjoy");
		supplier.setSupplierMobileNo("9003925123");
		assertTrue("Problem in adding supplier",supplierDAO.addSupplier(supplier));
	}
	
	@Ignore
	@Test
	public void updateSupplierTest() {
		Supplier supplier = supplierDAO.viewSupplier(104);
		supplier.setSupplierName("Cateline Stark");
		assertTrue("Problem in updating supplier",supplierDAO.updateSupplier(supplier));
	}
	
	@Ignore
	@Test
	public void deleteSupplierTest() {
		Supplier supplier = supplierDAO.viewSupplier(105);
		assertTrue("Problem in deleting supplier details",supplierDAO.deleteSupplier(supplier));
	}
	@Ignore
	@Test
	public void viewSupplierTest() {
		assertNotNull("Problem in viewing the supplier details", supplierDAO.viewSupplier(103));
		System.out.println("Supplier ID is "+supplierDAO.viewSupplier(103).getSupplierId());
		System.out.println("Supplier Name is "+supplierDAO.viewSupplier(103).getSupplierName());
		System.out.println("Supplier Mobile is "+supplierDAO.viewSupplier(103).getSupplierMobileNo());
	}
	
	
	@Test
	public void listSuppliersTest() {
		assertNotNull("Problem in listing supplier details",supplierDAO.listSuppliers());
		System.out.println("Supplier ID   Supplier Name\t\t Supplier Mobile No");
		for(Supplier supplier:supplierDAO.listSuppliers()) {
			System.out.print(supplier.getSupplierId()+"\t\t");
			System.out.print(supplier.getSupplierName()+"\t\t");
			System.out.println(supplier.getSupplierMobileNo());
		}
	}
}
