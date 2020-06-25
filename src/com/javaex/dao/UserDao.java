package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

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

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	private void close() {
		// 자원정리
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
	
	public int insert(UserVo uVo) {
		int count = 0;
		getConnection();
		
		String sql = "";
		sql += " insert into users ";
		sql += " values(seq_users_no.nextval, ?, ?, ?, ?) ";

		try {
			pstmt = conn.prepareStatement(sql); // 쿼리로 만들기
			
			pstmt.setString(1, uVo.getId());
			pstmt.setString(2, uVo.getPassword());
			pstmt.setString(3, uVo.getName());
			pstmt.setString(4, uVo.getGender());
			
			count = pstmt.executeUpdate(); // 쿼리문 실행
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		close();
		return count;
	}
	
	public UserVo getUser(String id, String password) {
		getConnection();
		
		UserVo uVo = null;
		
		String sql = "";
		sql += " select no, name from users ";
		sql += " where id = ? and password = ? ";
		System.out.println(sql);
		try {
			
			pstmt = conn.prepareStatement(sql); // 쿼리로 만들기

			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				
				uVo = new UserVo();
				uVo.setNo(no);
				uVo.setName(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		close();
		return uVo;
	}
	
	public int update(UserVo uVo) {
		System.out.println("update 실행");
		System.out.println(uVo.getNo());
		int count = 0;
		getConnection();
		
		String sql = "";
		sql += " update users ";
		sql += " set password = ?, ";
		sql += " name = ?, ";
		sql += " gender = ? ";
		sql += " where no = ? ";


		try {
			pstmt = conn.prepareStatement(sql); // 쿼리로 만들기
			
			pstmt.setString(1, uVo.getPassword());
			pstmt.setString(2, uVo.getName());
			pstmt.setString(3, uVo.getGender());
			pstmt.setInt(4, uVo.getNo());
			
			count = pstmt.executeUpdate(); // 쿼리문 실행
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		close();
		return count;
	}
	
	
	
}
