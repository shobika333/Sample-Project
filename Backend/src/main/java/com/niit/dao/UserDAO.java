package com.niit.dao;

import java.util.List;

import com.niit.model.User;

public interface UserDAO {
	public boolean addUserDetail(User user);
	public boolean deleteUserDetail(User user);
	public boolean updateUserDetail(User user);
	public User viewUserDetailByEmail(String emailId);
	public User viewUserDetailByUsername(String username);
	public List<User> listUser();
}
