package com.myweb.board.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.board.model.BoardDAO;
import com.myweb.board.model.BoardVO;

public class SearchService implements IBoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String category = request.getParameter("category");
		String keyword = request.getParameter("search");
		List<BoardVO> list = BoardDAO.getInstance().searchBoard(keyword, category);
		
		if(list.size() == 0 ) { // 검색결과가 없었다면~
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				String htmlCode =  "<script>\r\n"
						+ "alert('검색결과에 따른 게시물이 없습니다.');\r\n"
						+ "location.href='/MyWeb/list.board';\r\n" 
						+ "</script>";
				out.print(htmlCode);
				out.flush(); // 밖으로 밀어내 줌.
				return;  // 조회 결과가 없었다면 request에 데이터를 담지 않아도 되기 때문에 메서드를 강제종료
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		request.setAttribute("boardList", list);
		
	}

}
