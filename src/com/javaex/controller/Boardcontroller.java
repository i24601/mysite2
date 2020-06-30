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
		List<BoardVo> bList;
		String str = "";
		int no1 = 0;
		BoardVo bVo;
		System.out.println(action);
		switch (action) {
			case "list" : 
				
				/*
				 * session = request.getSession(); UserVo uVo =
				 * (UserVo)session.getAttribute("authUser"); System.out.println(uVo.toString());
				 * System.out.println(uVo.getNo());
				 */
				
				str = request.getParameter("str")==null?"":request.getParameter("str");
				
				bDao=new BoardDao();
				bList = bDao.getPersonList(str);
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
				bVo = new BoardVo(0,0,uVo.getNo(),title, content, null, uVo.getName());
				bDao.BoardInsert(bVo);
				WebUtil.redeirect(request, response, "/mysite2/bc?action=list");

			break;
			
			case "delete" :
				int no = Integer.parseInt(request.getParameter("no"));	
				bDao = new BoardDao(); bDao.boardDelete(no);
				WebUtil.redeirect(request, response, "/mysite2/bc?action=list");
			break;
			
			case "read" :
				bDao=new BoardDao();
				no1 = Integer.parseInt(request.getParameter("no"));
				bList = bDao.getPersonList(no1);
				System.out.println("read"+bList.toString());
				bVo = new BoardVo(bList.get(0).getNo(), bList.get(0).getHit(), bList.get(0).getUser_no(), bList.get(0).getTitle(), bList.get(0).getContent(), bList.get(0).getReg_date(), bList.get(0).getName());
				request.setAttribute("bVo", bVo);
				bDao.increaseHit(no1);
				WebUtil.foword(request, response,"/WEB-INF/views/board/read.jsp");
			break;
			
			case "modifyForm" :
				bDao=new BoardDao();
				no1 = Integer.parseInt(request.getParameter("no"));
				bList = bDao.getPersonList(no1);
				bVo = new BoardVo(0, bList.get(0).getHit(), 0, bList.get(0).getTitle(), bList.get(0).getContent(), bList.get(0).getReg_date(), bList.get(0).getName());
				request.setAttribute("bVo", bVo);
				WebUtil.foword(request, response,"/WEB-INF/views/board/modifyForm.jsp");
			break;
			
			case "modify" :
				bDao=new BoardDao();
				session = request.getSession();
				uVo = (UserVo)session.getAttribute("authUser");
				title = request.getParameter("title");
				content = request.getParameter("content");
				no1 = Integer.parseInt(request.getParameter("no"));
				bList = bDao.getPersonList(no1);
				System.out.println(bList.toString());
				System.out.println("게시글 번호");
				System.out.println(bList.get(0).getNo());
				bVo = new BoardVo(bList.get(0).getNo(), bList.get(0).getHit(), bList.get(0).getUser_no(), title, content, bList.get(0).getReg_date(), bList.get(0).getName());
				
				bDao.update(bVo);
				WebUtil.redeirect(request, response, "/mysite2/bc?action=list");

			break;
		}
		 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
