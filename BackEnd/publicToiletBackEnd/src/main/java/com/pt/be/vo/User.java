package com.pt.be.vo;

import java.util.Random;

public class User {
	private String name;
	private String id;
	private String pw;
	private int isAdmin;
	private String phoneNumber;
	private String authCode;
	public User(String id) {
		super();
		this.name = "";
		this.id = id;
		this.pw = "";
		this.isAdmin = 0;
		this.phoneNumber = "";
		this.authCode = createRandomCode(12);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	static public String createRandomCode(int length) {
		// 12자리 인증 코드 생성
		char[] code = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
				'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', '!', '@', '#', '$', '%', '^', '&', '~', '.', '!', '?' };

		String authCode = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			authCode += code[random.nextInt(code.length)];
		}

		return authCode;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", id=" + id + ", pw=" + pw + ", isAdmin=" + isAdmin + ", phoneNumber="
				+ phoneNumber + ", authCode=" + authCode + "]";
	}
}
