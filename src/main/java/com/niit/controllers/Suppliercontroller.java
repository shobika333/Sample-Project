package com.niit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.SupplierDAO;
import com.niit.model.Supplier;

@Controller
public class Suppliercontroller {
	@Autowired
	SupplierDAO supplierDAO;
	
	@RequestMapping(value="/viewSupplierAddPage")
	public String viewAddSupplier(Model m) {
		Supplier sup = new Supplier();
		m.addAttribute("supplier", sup);
		return "addSupplier";
	}
	
	@RequestMapping(value="/addSupplier", method=RequestMethod.POST)
	public String addSupplier(@ModelAttribute("supplier") Supplier supplier, Model m) {
		supplierDAO.addSupplier(supplier);
		List<Supplier> supplierList = supplierDAO.listSuppliers();
		m.addAttribute("supplierList", supplierList);
		return "viewSupplierDetails";
	}
	
	@RequestMapping(value="/viewSupplierDetails")
	public String viewSupplierList(Model m) {
		List<Supplier> supplierList = supplierDAO.listSuppliers();
		m.addAttribute("supplierList", supplierList);
		return "viewSupplierDetails";
	}
	
	@RequestMapping(value="/editSupplierDetails/{supplierId}", method=RequestMethod.GET)
	public String editSupplierDetails(@PathVariable("supplierId")int supplierId, Model m) {
		Supplier supplier = supplierDAO.viewSupplier(supplierId);
		m.addAttribute("supplier", supplier);
		return "updateSupplier";
	}
	
	@RequestMapping(value="/updateSupplier", method=RequestMethod.POST)
	public String updateSupplier(@ModelAttribute("supplier") Supplier supplier, Model m) {
		supplierDAO.updateSupplier(supplier);
		List<Supplier> supplierList = supplierDAO.listSuppliers();
		m.addAttribute("supplierList", supplierList);
		return "viewSupplierDetails";
	}
	
	@RequestMapping(value="/deleteSupplierDetails/{supplierId}", method=RequestMethod.GET)
	public String deleteSupplierDetails(@PathVariable("supplierId")int supplierId, Model m) {
		Supplier supplier = supplierDAO.viewSupplier(supplierId);
		supplierDAO.deleteSupplier(supplier);
		List<Supplier> supplierList = supplierDAO.listSuppliers();
		m.addAttribute("supplierList", supplierList);
		return "viewSupplierDetails";		
	}
	
}