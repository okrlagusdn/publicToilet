package com.pt.be.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pt.be.vo.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SqlSession sqlSession;
	private final String namespace = "userMapper.";

	@Override
	public boolean checkIdDuplication(String id) throws Exception {
		return (Integer) sqlSession.selectOne(namespace+"checkIdDuplication", id)==1?true:false;
	}

	@Override
	public boolean create(User user) throws Exception {
		return sqlSession.insert(namespace+"create", user)>0?true:false;
	}

	@Override
	public User get(String id) throws Exception {
		return sqlSession.selectOne(namespace + "get", id);
	}
}
