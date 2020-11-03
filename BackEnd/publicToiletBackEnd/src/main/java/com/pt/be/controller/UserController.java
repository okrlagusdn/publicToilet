package com.pt.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pt.be.service.UserService;
import com.pt.be.vo.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "User" })
public class UserController {
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "계정명이 id인 사용자를 생성하고 해당 사용자의 인증코드를 반환한다.")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody String id) throws Exception {
		id = id.trim().replaceAll("\"", "");
		User user = null;
		ObjectMapper mapper = new ObjectMapper();
		
		//기존DB에 id가 존재하는 경우
		if(userService.checkIdDuplication(id)) {
			user = userService.get(id);
			//인증란이 pass인 경우, 즉 회원가입이 완료된 id인 경우
			if(user.getAuthCode().equals("pass") == true) {
				return new ResponseEntity<String>(mapper.writeValueAsString(1), HttpStatus.OK);
			}
			user.setAuthCode(User.createRandomCode(12));
			//userService.update(user);
		}else {
			user = new User(id);
			userService.create(user);
		}
		return new ResponseEntity<String>(mapper.writeValueAsString(0), HttpStatus.OK);
	}
}
