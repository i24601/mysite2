package com.javaex.vo;

public class BoardVo {
	private int no, hit, user_no;
	private String title, content, reg_date, name;

	public BoardVo() {
	}
	
	public BoardVo(int no, int hit, int user_no, String title, String content, String reg_date, String name) {
		this.no = no;
		this.hit = hit;
		this.user_no = user_no;
		this.title = title;
		this.content = content;
		this.reg_date = reg_date;
		this.name = name;
	}



	public int getNo() {
		return no;
	}



	public void setNo(int no) {
		this.no = no;
	}



	public int getHit() {
		return hit;
	}



	public void setHit(int hit) {
		this.hit = hit;
	}



	public int getUser_no() {
		return user_no;
	}



	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", hit=" + hit + ", user_no=" + user_no + ", title=" + title + ", content="
				+ content + ", reg_date=" + reg_date + ", name=" + name + "]";
	}
	
	
	
	
	
	
}
