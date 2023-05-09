package com.myweb.board.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.board.model.BoardDAO;
import com.myweb.board.model.BoardVO;

public class ContentService implements IBoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		int bId = Integer.parseInt(request.getParameter("bId")); //bId(글번호) 를 가져옴. getpara는 String으로 값을 가져오므로 int로 바꿔줌.
		BoardDAO dao = BoardDAO.getInstance();
		
		/*
        # 쿠키로 조회수를 컨트롤 해 보자.
        1. 사용자가 글 제목을 눌러서 상세보기 요청을 보낼 때 
        글 번호로 된 쿠키를 하나 만들어 줄 겁니다. (String)
        쿠키 이름과 쿠키에 저장할 값을 모두 글 번호로 만들어 주겠습니다.
        쿠키의 수명은 15초로 설정하겠습니다.
        
        2. 요청을 보낼 때 같이 넘어온 쿠키 중에, 
         현재 글 번호와 일치하는 쿠키가 존재한다면 조회수를 올려주지 않을 겁니다.
         현재 글 번호와 일치하는 쿠키가 없다면 조회수를 올려주도록 하겠습니다.  
        */
		
		Cookie[] cookies = request.getCookies(); //쿠키를 요청한다, 쿠키가 여러개니 배열로 받는다. getcookies()에 올려서 배열로받는 것 확인.
		boolean flag = false; //내가 찾는 쿠키의 존재유무를 파악할 변수
		if(cookies != null) {//쿠키가 존재한다면
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(bId+"")) { 
			
					flag=true; 
					break;
				} 
				
			}
			
		} if(!flag) { //쿠키가 존재하지 않는다면
			dao.upHit(bId);
			
			Cookie c = new Cookie(bId+"", "c" );
			c.setMaxAge(15);
			response.addCookie(c); //서버에 올림.
		}
		
//		dao.upHit(bId); // 조회수 올리기 contentBoard부르기 전에 조회수 먼저 올려줘야함.
		BoardVO vo = dao.contentBoard(bId);
		vo.setContent(vo.getContent().replace("\r\n", "<br>")); //줄개행 \r\n을 전부 <br>로 변경.
		
		
		request.setAttribute("content", vo); //리퀘스트에 content이름으로 BoardDAO에서 리턴된 vo를 담는다.
	}

}
