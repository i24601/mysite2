package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestBookVo;
import com.javaex.vo.UserVo;

@WebServlet("/bc")
public class Boardcontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session; 
		BoardDao bDao;
		UserVo uVo;
		String title, content;
		System.out.println(action);
		switch (action) {
			case "list" : 
				
				/*
				 * session = request.getSession(); UserVo uVo =
				 * (UserVo)session.getAttribute("authUser"); System.out.println(uVo.toString());
				 * System.out.println(uVo.getNo());
				 */
				bDao=new BoardDao();
				List<BoardVo> bList = bDao.getPersonList();
				request.setAttribute("bList", bList);
				
				WebUtil.foword(request, response,"/WEB-INF/views/board/list.jsp");
			break;
				
			case "writeForm" : 
				WebUtil.foword(request, response,"/WEB-INF/views/board/writeForm.jsp");
			break;
			
			case "write" : 
				bDao = new BoardDao();
				session = request.getSession();
				uVo = (UserVo)session.getAttribute("authUser");
				
				title = request.getParameter("title");
				content = request.getParameter("content");
				BoardVo bVo = new BoardVo(0,0,uVo.getNo(),title, content, null, uVo.getName());
				bDao.BoardInsert(bVo);
				WebUtil.redeirect(request, response, "/mysite2/bc?action=list");

			break;
			
			case "delete" :
				int no = Integer.parseInt(request.getParameter("no"));	
				bDao = new BoardDao(); bDao.boardDelete(no);
				WebUtil.redeirect(request, response, "/mysite2/bc?action=list");
			break;
			
			case "modifyForm" :
				WebUtil.foword(request, response,"/WEB-INF/views/board/modifyForm.jsp");
			break;
		}
		 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
