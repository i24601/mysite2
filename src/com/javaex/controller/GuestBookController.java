package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;

@WebServlet("/gbc")
public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		GuestBookDao gDao = new GuestBookDao();
		System.out.println("액션은"+action);
		switch(action) {
		case "addlist" : 
			List<GuestBookVo> gList = gDao.getPersonList();
			request.setAttribute("gList", gList);
			WebUtil.foword(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			break;
		case "add" :
			String name = request.getParameter("name");
			String password_write = request.getParameter("pass");
			String content = request.getParameter("content");
			GuestBookVo gVo = new GuestBookVo(name, password_write, content);
			gDao.GuestBookInsert(gVo);
			WebUtil.redeirect(request, response, "/mysite2/gbc?action=addlist");
			break;
		case "deleteForm" :
			WebUtil.foword(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			break;
		case "delete" :
			System.out.println("삭제");
			int no = Integer.parseInt(request.getParameter("no"));
			String password_delete = request.getParameter("pass");
			System.out.println(no);
			System.out.println(password_delete);
			
			gDao.guestDelete(no, password_delete);
			System.out.println("삭제완료");
			WebUtil.redeirect(request, response, "/mysite2/gbc?action=addlist");
			break;
		default : System.out.println(action);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
