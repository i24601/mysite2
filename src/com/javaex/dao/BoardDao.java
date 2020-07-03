package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.GuestBookVo;
import com.javaex.vo.UserVo;

public class BoardDao {
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public int BoardInsert(BoardVo bVo) {
		int count = 0;
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " INSERT INTO board ";
			query += " VALUES (seq_board_no.nextval, ?, ?, ?, sysdate, ?) ";
			System.out.println(query);
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			
			pstmt.setString(1, bVo.getTitle()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setString(2, bVo.getContent()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setInt(3, bVo.getHit()); // ?(물음표) 중 1번째, 순서중요
			pstmt.setInt(4, bVo.getUser_no());
			
			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println("[" + count + "건 추가되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return count;
	}
	
	public BoardVo getPersonList(int no1) {
		BoardVo bVo = null;
		getConnection();
		try {
			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " select  b.no, ";
			query += "         b.title, ";
			query += "         b.content, ";
			query += "         b.hit, ";
			query += "         to_char(b.reg_date,'YYYY-MM-DD HH24:MI') as reg_date , ";
			query += "         b.user_no, ";
			query += "         u.name ";
			query += " from users u, board b ";
			query += " where u.no = b.user_no ";
			query += " and b.no = ? ";
			
				System.out.println(query);
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
				pstmt.setInt(1, no1);
				
			System.out.println(query);
			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String reg_date = rs.getString("reg_date");
				int user_no = rs.getInt("user_no");
				String name = rs.getString("name");
				
				bVo = new BoardVo(no, hit, user_no, title, content, reg_date, name, 0);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return bVo;

	}
	
	public int boardDelete(int no) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " delete from board ";
			query += " where no = ? ";
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			System.out.println(query);
			pstmt.setInt(1, no);// ?(물음표) 중 1번째, 순서중요

			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	
	public int increaseHit(int no1) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " update board ";
			query += " set hit = hit + 1 ";
			query += " where no = ? ";
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setInt(1, no1);
			System.out.println(query);
			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	
	public int update(BoardVo bVo) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " update board ";
			query += " set title = ?, ";
			query += " content = ? ";
			query += " where no = ? ";
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			pstmt.setString(1, bVo.getTitle());
			System.out.println(bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			System.out.println(bVo.getContent());
			pstmt.setInt(3, bVo.getNo());
			System.out.println(bVo.getNo());
			System.out.println(query);
			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	public void getPersonList(String str) {
		getPersonList(str, 0);
	}
	
	public List<BoardVo> getPersonList(String str, int page) {
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " SELECT  B.brdNo, ";
			query += "         B.brdTitle, ";
			query += "         B.brdContent, ";
			query += "         B.usrName, ";
			query += "         B.brdHit, ";
			query += "         to_char(B.brdRegDate, 'YYYY-MM-DD HH24:MI') as brdRegDate, ";
			query += "         B.usrNo, ";
			query += "         B.TOTCNT ";
			query += " FROM ";
			query += "         ( ";
			query += "     	   		SELECT ";
			query += "     	   			A.brdNO, ";
			query += "     	   			A.brdTitle, ";
			query += "     	   			A.brdContent, ";
			query += "     	   			A.usrName, ";
			query += "     	   			A.brdHit, ";
			query += "     	   			A.brdRegDate, ";
			query += "     	   			A.usrNo, ";
			query += "     	   			ROWNUM AS RNUM, ";
			query += "     	   			COUNT(*) OVER() AS TOTCNT ";
			query += "     	   		FROM ";
			query += "         			( ";
			query += "     	   				SELECT ";
			query += "         					b.no as brdNo, ";
			query += "         					b.title as brdTitle, ";
			query += "         					b.content as brdContent, ";
			query += "         					u.name as usrName, ";
			query += "         					b.hit as brdHit, ";
			query += "         					b.reg_date as brdRegDate, ";
			query += "         					u.no as usrNo ";
			query += "     	   				FROM ";
			query += "         					users u, board b ";
			query += "         					where u.no = b.user_no ";
			
			if(str!="") {
			query += "         					and b.title like ? ";
						}
			
			query += "     	   				ORDER BY ";
			query += "         					b.no desc ";
			query += "         			) A";
			query += "         ) B";
			query += " WHERE ";
			query += " 		RNUM between ? and ? ";
			System.out.println(query);
			if(str!="") {
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
				pstmt.setString(1, '%' + str + '%');
//				if(page!=0) 
//				{
//				pstmt.setInt(2, page);
//				pstmt.setInt(3, page+2);
//				}
				if(page!=0) 
				{
				pstmt.setInt(1, 1+3*(page-1)); 
				pstmt.setInt(2, 3*page);
				}
			}
			else {
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
				if(page!=0) 
				{
				pstmt.setInt(1, 1+3*(page-1)); 
				pstmt.setInt(2, 3*page);
				}
			}
			System.out.println(query);
			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("brdNo");
				int hit = rs.getInt("brdHit");
				int user_no = rs.getInt("usrNo");
				String title = rs.getString("brdTitle");
				String content = rs.getString("brdContent");
				String reg_date = rs.getString("brdRegdate");
				String name = rs.getString("usrName");
				int count = rs.getInt("TOTCNT");
				BoardVo bVo = new BoardVo(no, hit, user_no, title, content, reg_date, name, count);
				boardList.add(bVo);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return boardList;

	}
	
	
	
}
