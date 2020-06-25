package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); //한글깨짐 방지
		String action = request.getParameter("action");
		
		String id = null, password = null, name = null, gender = null;
		UserVo uVo = null ;
		UserDao uDao = null ;
		HttpSession session;
		
		switch (action) {
		case "joinForm" : 
			WebUtil.foword(request, response, "/WEB-INF/views/user/joinForm.jsp");
			break;
			
		case "join" : 
			id = request.getParameter("id");
			password = request.getParameter("password");
			name = request.getParameter("name");
			gender = request.getParameter("gender");
			
			uVo = new UserVo(id, password, name, gender);
			uDao = new UserDao();
			
			uDao.insert(uVo);
			
			WebUtil.foword(request, response, "/WEB-INF/views/user/joinOk.jsp");
			break;
			
		case "loginForm" :
			WebUtil.foword(request, response, "/WEB-INF/views/user/loginForm.jsp");
			break;
		
		case "login" :
			System.out.println("login");
			uDao = new UserDao();
			id = request.getParameter("id");
			password = request.getParameter("password");
			System.out.println(id);
			System.out.println(password);
			uVo = uDao.getUser(id,password);
			
			if(uVo == null) {
				System.out.println("로그인 실패");
				WebUtil.redeirect(request, response, "/mysite2/user?action=loginForm&result=fail");
			}
			
			else {
				//세션영역에 값을 추가
				session = request.getSession();
				session.setAttribute("authUser", uVo);
				
				//로그인성공
				WebUtil.redeirect(request, response, "/mysite2/main");
			}
			break;
			
		case "logout" :
			session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redeirect(request, response, "/mysite2/main");
			break;
			
		case "modifyForm" :
			WebUtil.foword(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			break;
			
		case "modify" :
			uDao = new UserDao();
			
			session = request.getSession();
			uVo = (UserVo)session.getAttribute("authUser");
			session.removeAttribute("authUser");
			
			int no = uVo.getNo();
			password = request.getParameter("password");
			name = request.getParameter("name");
			gender = request.getParameter("gender");

			uVo = new UserVo(no, password, name, gender);
			uDao.update(uVo);

			session.setAttribute("authUser", uVo);
			
			WebUtil.redeirect(request, response, "/mysite2/main");
			break;
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}


