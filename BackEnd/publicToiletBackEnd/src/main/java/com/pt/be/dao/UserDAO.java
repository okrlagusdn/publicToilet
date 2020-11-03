package com.pt.be.dao;

import com.pt.be.vo.User;

public interface UserDAO {
	boolean checkIdDuplication(String id) throws Exception;
	boolean create(User user) throws Exception;
	User get(String id) throws Exception;
}
