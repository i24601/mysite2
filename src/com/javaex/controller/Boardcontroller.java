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
		int page;
		int no1 = 0;
		BoardVo bVo;
		System.out.println(action);
		switch (action) {
			case "list" : 	
				bDao=new BoardDao();
				str = request.getParameter("str")==null?"":request.getParameter("str");

				page = Integer.parseInt(request.getParameter("page"));

				bList = bDao.getPersonList(str, page);
				int count = bList.get(0).getCount();
				int size = count%3==0?(count/3):(count/3+1);
				
				if(page<=10 && page>=1) {
				int start = 1;
				int last = 10;
				}
				request.setAttribute("bList", bList);
				request.setAttribute("size", size);
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
				bVo = new BoardVo(0,0,uVo.getNo(),title, content, null, uVo.getName(),0);
				bDao.BoardInsert(bVo);
				
				page = Integer.parseInt(request.getParameter("page"));
				str = request.getParameter("str")==null?"":request.getParameter("str");
				WebUtil.redeirect(request, response, "/mysite2/bc?action=list&page="+page+"&str="+str);

			break;
			
			case "delete" :
				int no = Integer.parseInt(request.getParameter("no"));	
				bDao = new BoardDao(); bDao.boardDelete(no);
				
				page = Integer.parseInt(request.getParameter("page"));
				str = request.getParameter("str")==null?"":request.getParameter("str");
				WebUtil.redeirect(request, response, "/mysite2/bc?action=list&page="+page+"&str="+str);
			break;
			
			case "read" :
				bDao=new BoardDao();
				no1 = Integer.parseInt(request.getParameter("no"));
				bVo = bDao.getPersonList(no1);
				request.setAttribute("bVo", bVo);
				bDao.increaseHit(no1);
				WebUtil.foword(request, response,"/WEB-INF/views/board/read.jsp");
			break;
			
			case "modifyForm" :
				bDao=new BoardDao();
				no1 = Integer.parseInt(request.getParameter("no"));
				bVo = bDao.getPersonList(no1);
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
				System.out.println("no1은"+no1);
				System.out.println("게시글 번호");
				bVo = new BoardVo(no1, 0, 0, title, content, null, null, 0);
				bDao.update(bVo);
				
				page = Integer.parseInt(request.getParameter("page"));
				str = request.getParameter("str")==null?"":request.getParameter("str");
				WebUtil.redeirect(request, response, "/mysite2/bc?action=list&page="+page+"&str="+str);

			break;
		}
		 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
