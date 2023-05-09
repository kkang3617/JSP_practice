package com.myweb.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardDAO implements IBoardDAO {

		//커넥션 풀 정보를 담을 변수 선언
		private DataSource ds;
		
		private BoardDAO() { //객체가 생성되자마자
			//커넥션 풀 정보 불러와라.
			try {
				InitialContext ct = new InitialContext();
				ds = (DataSource) ct.lookup("java:comp/env/jdbc/myOracle");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		private static BoardDAO dao = new BoardDAO(); //객체생성
			
		public static BoardDAO getInstance() {
			if(dao == null) {
				dao = new BoardDAO();
			}
			return dao;
		}
	
		///////////////////////////////////////////////////////////////////////
		
	@Override
	public void regist(String writer, String title, String content) {
		String sql = "INSERT INTO my_board(board_id , writer, title, content)" //컬럼명작성
					+ "VALUES (board_seq.NEXTVAL,?,?,?)"; // 첫번째 board_id 는 시퀀스 이용
		
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, writer); //물음표 첫번째
			pstmt.setString(2, title);  //물음표 두번째
			pstmt.setString(3, content); //물음표 세번째
			pstmt.executeUpdate(); //실행 INSERT,UPDATE,DELETE executeUpdate, SELECT executeQuery
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	@Override
	public List<BoardVO> listBoard() {
		List<BoardVO> articles = new ArrayList<>();
		String sql = "SELECT * FROM my_board ORDER BY board_id DESC";
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			
			while(rs.next()) {
				BoardVO vo = new BoardVO(
						rs.getInt("board_id"),
						rs.getString("writer"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getTimestamp("reg_date").toLocalDateTime(),
						rs.getInt("hit")
						);
				articles.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public BoardVO contentBoard(int bId) {
		BoardVO vo = null;
		String sql = "SELECT * FROM my_board WHERE board_id=?";
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, bId); //첫번째 물음표
			ResultSet rs = pstmt.executeQuery(); // sql 문실행. 여기까지가 sql문 
			
			if(rs.next()) {
				vo = new BoardVO( //객체 포장하기
						rs.getInt("board_id"),
						rs.getString("writer"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getTimestamp("reg_date").toLocalDateTime(),
						rs.getInt("hit")
						);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo; // 객체포장된 값 리턴
	}

	@Override
	public void updateBoard(String title, String content, int bId) {
		
		String sql = "UPDATE my_board SET title=?, content=? WHERE board_id=" + bId ; // update 조회테이블 set 수정할컬럼=? where 조건
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteBoard(int bId) {
		String sql = "DELETE FROM my_board WHERE board_id=" + bId;
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<BoardVO> searchBoard(String keyword, String category) {
		List<BoardVO> searchList = new ArrayList<>();
		String sql = "SELECT * FROM my_board "
				+ "WHERE " + category + " like ?";
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery(); // 여기까지 sql , 실행
			while(rs.next()) {
				BoardVO vo = new BoardVO(  // 객체포장
						rs.getInt("board_id"),
						rs.getString("writer"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getTimestamp("reg_date").toLocalDateTime(),
						rs.getInt("hit")
						);
				searchList.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return searchList;
	}
	
	@Override
	public void upHit(int bId) {
		String sql = "UPDATE my_board SET hit=hit+1 WHERE board_id=?";
		try(Connection conn = ds.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, bId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
}
