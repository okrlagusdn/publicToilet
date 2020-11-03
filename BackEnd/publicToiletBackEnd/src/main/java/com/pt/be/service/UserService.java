package com.pt.be.service;

import com.pt.be.vo.User;

public interface UserService {
	boolean checkIdDuplication(String id) throws Exception;
	boolean create(User user) throws Exception;
	User get(String id) throws Exception;
}
