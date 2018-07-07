package com.niit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import org.springframework.stereotype.Component;

@Table(name="User")
@Entity
@Component
public class User {
	@Id
	@GeneratedValue
	private int Id;
	 
private String username;
private boolean enabled;
private String role;

private String name;

private String emailId;

private String password;

private String mobileNo;

private String address;

private int zipcode;

public boolean isEnabled() {
	return enabled;
}
public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}

public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}


public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}

 

public String getName() {
	return name;
}
public void setName( String name) {
	this.name=name;
	
}


public String getEmailId() {
	return emailId;
}
public void setEmailId( String emailId) {
	this.emailId=emailId;
}


public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password=password;
}



public String getMobileNo() {
	return mobileNo;
}
public void setMobileNumber( String mobileNo) {
	this.mobileNo=mobileNo;
}



public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address=address;
}


public int getZipcode() {
	return zipcode;
}
public void setZipcode(int zipcode) {
	this.zipcode=zipcode;
}
}
  