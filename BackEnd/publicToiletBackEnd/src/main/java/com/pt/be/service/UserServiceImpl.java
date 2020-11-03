package com.pt.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pt.be.dao.UserDAO;
import com.pt.be.vo.User;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public boolean checkIdDuplication(String id) throws Exception {
		return userDAO.checkIdDuplication(id);
	}

	@Override
	public boolean create(User user) throws Exception {
		return userDAO.create(user);
	}

	@Override
	public User get(String id) throws Exception {
		return userDAO.get(id);
	}
}
