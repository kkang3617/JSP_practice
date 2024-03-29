package com.myweb.user.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.connector.Response;

//DAO(data Access Object)
//웹 프로그램에서 데이터베이스 CRUD 작업이 요구될 때마다
//데이터베이스 접속 및 sql문 실행을 전담하는 비즈니스 로직으로 이루어진 객체
//무분별한 객체 생성을 막기 위해 싱글톤 디자인 패턴으로 구축한다.
public class UserDAO {
	
	//커넥션 풀 정보를 담을 변수 선언
	private DataSource ds;
	
	private UserDAO() {
		//커넥션 풀 정보 불러오기.
		try {
			InitialContext ct = new InitialContext();
			ds = (DataSource) ct.lookup("java:comp/env/jdbc/myOracle");
		} catch (NamingException e) {
			
			e.printStackTrace();
		}
	}
	
	private static UserDAO dao = new UserDAO();
		
	public static UserDAO getInstance() {
		if(dao == null) {
			dao = new UserDAO();
		}
		return dao;
	}
	
	/////////////////////////////////////////////////////////
	
	//회원 중복 여부 확인
	public boolean confirmId(String id) {
		String sql = "SELECT * FROM my_user WHERE user_id=?";
		boolean flag = false;
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) flag = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;	
	}

	public void insertUser(UserVO vo) {
		String sql = "INSERT INTO my_user VALUES(?,?,?,?,?)";
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, vo.getUserId());
			pstmt.setString(2, vo.getUserPw());
			pstmt.setString(3, vo.getUserName());
			pstmt.setString(4, vo.getUserEmail());
			pstmt.setString(5, vo.getUserAddress());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int userCheck(String id, String pw) { //id pw 일치 1, id 없으면 -1 , id비번 불일치 0
		int check = 0;
		String sql = "SELECT user_pw FROM my_user "
					+ "WHERE user_id=?";
		
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {  // 조회결과 있음(id가 있음) pw로 판별 가능해서 위 sql처럼 씀.
				String dbPw = rs.getString("user_pw");
				if(dbPw.equals(pw)) { //db에 있는 pw가 입력pw와 같다면
					check = 1;  //check 1 리턴
				} else {
					check = 0;  // 다르다면 check 0 리턴
				}
			} else { // 조회결과가 없으면(행이 없으면)
				check = -1;
			}		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return check;
				
		
		
	}

	public UserVO getUserInfo(String id) {
		UserVO user = null;
		String sql = "SELECT * FROM my_user"
					+ " WHERE user_id='" + id + "'"; // WHWER절 조건 id=입력한id
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			if(rs.next()) {
				user = new UserVO(
							rs.getString("user_id"),
							rs.getString("user_pw"),
							rs.getString("user_name"),
							rs.getString("user_email"),
							rs.getString("user_address")
						);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return user;
	}

	public void changePassword(String userId, String newPw) {
		String sql = "UPDATE my_user SET user_pw = ? WHERE user_id= ?";
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, newPw);
			pstmt.setString(2, userId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		 
	}

	public void updateUser(UserVO vo) {
		String sql = "UPDATE my_user "
				+ "SET user_name=?, user_email=?, user_address=? "
				+ "WHERE user_id = ?";
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, vo.getUserName());
			pstmt.setString(2, vo.getUserEmail());
			pstmt.setString(3, vo.getUserAddress());
			pstmt.setString(4, vo.getUserId());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public int deleteUser(String id) { //vo.getUserId()
		String sql = "DELETE FROM my_user WHERE user_id=?";
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id); //vo.getUserId()
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

	

	
}
