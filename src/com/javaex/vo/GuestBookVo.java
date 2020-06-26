package com.javaex.vo;

public class GuestBookVo {

	private int number;
	private String name, password, content, reg_date;	

	public GuestBookVo(int number, String name, String content, String reg_date) {
		this.number = number;
		this.name = name;
		this.content = content;
		this.reg_date = reg_date;
	}
	
	public GuestBookVo(String name, String password, String content) {
		this.name = name;
		this.password = password;
		this.content = content;
	}


	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	
}