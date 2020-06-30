package com.javaex.dao;

import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardTest {

	public static void main(String[] args) {
		BoardDao bDao = new BoardDao();
		List<BoardVo> bList = bDao.getPersonList();
		System.out.println(bList.toString());
	}

}
