package com.myweb.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.board.model.BoardDAO;
import com.myweb.board.service.ContentService;
import com.myweb.board.service.GetListService;
import com.myweb.board.service.IBoardService;
import com.myweb.board.service.ModifyService;
import com.myweb.board.service.SearchService;
import com.myweb.board.service.UpdateService;


@WebServlet("*.board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	
	private IBoardService sv;
    private RequestDispatcher dp;
	
    public BoardController() {
        super();
     
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getMethod().equals("POST")) { //요청방식이 POST 방식이면(get은 한글 안깨짐 post만 깨짐.)
			request.setCharacterEncoding("utf-8"); //utf-8로 변경
		}
		
		String uri = request.getRequestURI();
		uri = uri.substring(request.getContextPath().length()+1, uri.lastIndexOf("."));
		
		System.out.println(uri);
		
		switch(uri) {
		
		case "write" :
			System.out.println("글쓰기 페이지로 이동 요청!");
			response.sendRedirect("board/board_write.jsp");
			break;
		
		case "regist" :
			System.out.println("글 등록 요청이 들어옴!");
			sv = new RegistService(); //RegistService() 서비스객체생성
			sv.execute(request, response); //execute라는 메서드를 부른다.
			
			/*
			 왜 board_list.jsp로 바로 리다이렉트 하면 안될까?
			 board_list.jsp에는 데이터베이스로부터 전체 글 목록을 가져오는
			 로직을 작성하지 않을 것 이기 때문. (jsp는 단순히 보여지는 역할만 수행)
			 컨트롤러로 글 목록 요청이 다시 들어올 수 있게끔
			 sendRedirect()를 사용하여 자동목록 재요청이 들어오게 하는 것이다.
			 단순 jsp만 요청하면 글목록 빈 껍데기만 보인다.
			 */
			response.sendRedirect("/MyWeb/list.board");
			
			break;
			
		case "list" :
			System.out.println("글 목록 요청이 들어옴!");
			sv = new GetListService();
			sv.execute(request, response);
			/*
			 여기서 sendRedirect를 하면 안되는 이유
			 request객체에 list를 담아서 전달하려 하는데, sendRedirect를
			 사용하면 응답이 바로 나가면서 request객체가 소멸해 버린다.
			 여기서는 forward방식을 사용해서 request를 원하는 jsp 파일로
			 전달해서 그쪽에서 응답이 나갈 수 있도록 처리해야한다. 
			 */
//			response.sendRedirect("board/board_list.jsp"); -> (x), 응답이 바로나간다.
			
			//request 객체를 다음 화면까지 운반하기 위한 forward 기능을 제공하는 객체
			// -> RequestDispatcher
			//forward -> 요청위임방식
			dp = request.getRequestDispatcher("board/board_list.jsp"); //request객체를 떠넘길 위치 board_list ->여기서 응답이 나간다.
			//requestDispatcher dp 를 위에 선언해줬음.
			dp.forward(request, response);
			break;
			
		case "content" :
			System.out.println("글 상세보기 요청이 들어옴!");
			sv = new ContentService();
			sv.execute(request, response);
			
			dp = request.getRequestDispatcher("board/board_content.jsp"); //request객체를 떠넘길 위치 board_content ->여기서 응답이 나간다.
			dp.forward(request, response);
			break;

		case "modify" :
			System.out.println("글 수정 페이지로 이동 요청!");
			sv = new ModifyService();
			sv.execute(request, response);
			
			dp = request.getRequestDispatcher("board/board_modify.jsp");
			dp.forward(request, response);
			break;
			
		case "update" :
			System.out.println("글 수정 요청이 들어옴!");
			sv = new UpdateService();
			sv.execute(request, response); //여까지 수정완료되었고,
			
			response.sendRedirect("/MyWeb/content.board?bId=" + request.getParameter("bId")); //글 상세보기 요청을 띄워라~
			break;																			// bId는 이전request 요청에서 사라졌기 때문에 다시한번 bId를 요청해줘야한다.
			
		case "delete" :
			System.out.println("글 삭제 요청이 들어옴!");
			BoardDAO.getInstance().deleteBoard(Integer.parseInt(request.getParameter("bId")));
			// BoardDAO주소값 받아오고,      deleteBoard에 bId(String(request)을 parseInt로 Int값변환)를 deleteBoard에 보낸다. 
 			response.sendRedirect("/MyWeb/list.board");
			break;
		
		case "search" :
			System.out.println("글 검색 요청이 들어옴!");
			sv = new SearchService();
			sv.execute(request, response);
			
			if(request.getAttribute("boardList") != null) {
				dp = request.getRequestDispatcher("board/board_list.jsp");
				dp.forward(request, response);
			}
			break;
			
		}
		
			
		
	}

}
