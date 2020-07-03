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
				
				int chapter = (int)Math.ceil(((float)page)/10);
				int btn_left;
				int btn_right;
				
				bList = bDao.getPersonList(str, page);
				
				System.out.println(page);
				System.out.println(bList.toString());
				int count=bList.get(0).getCount();
				
				System.out.println(count);
				
				request.setAttribute("bList", bList);
				
				if(count/3>=10) {
					//전체 page가 10개 이상
					System.out.println("10");
					request.setAttribute("start", 1+10*(chapter-1));
					if(chapter*30<=count) {
						//현재 챕터의 페이지가 10개이상
						System.out.println("10");
						request.setAttribute("end", chapter*10);
						if(chapter==1) {
							btn_left=1;
						}
						else {
							btn_left=10*(chapter-1);							
						}
						btn_right=chapter*10+1;
						request.setAttribute("btn_left", btn_left);
						request.setAttribute("btn_right", btn_right);
					}
					else {
						//현재 챕터의 페이지가 10개이하
						System.out.println("나누기");
						request.setAttribute("end", (chapter-1)*10+Math.ceil(((float)(count-30*(chapter-1)))/3));
						btn_left=10*(chapter-1);
						btn_right=(int)((chapter-1)*10+Math.ceil(((float)(count-30*(chapter-1)))/3));
						request.setAttribute("btn_left", btn_left);
						request.setAttribute("btn_right", btn_right);
					}
				}
				
				else{
					//전체페이지가 10개이하
					System.out.println("size");
					request.setAttribute("start", 1);
					request.setAttribute("end", Math.ceil(((float)count)/3) );
					btn_left=1;	
					btn_right=page;
					request.setAttribute("btn_left", btn_left);
					request.setAttribute("btn_right", btn_right);
				}
				
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
