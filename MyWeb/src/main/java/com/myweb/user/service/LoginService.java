package com.myweb.user.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserDAO;
import com.myweb.user.model.UserVO;

public class LoginService implements IUserService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		/*
	    1. 파라미터값 얻어오기 (id, pw)
	    2. DAO 주소값 얻어오기
	    3. 로그인 유효성 검증 메서드 userCheck() 호출하기.
	    - 아이디가 없다면 스크립트 경고창 출력 후 로그인 페이지로 이동 (-1)
	    - 비밀번호가 틀렸다면 비밀번호가 틀렸다고 경고창 출력 후 뒤로가기 (0)
	    - 로그인 성공일 경우 user_mypage.jsp로 리다이렉팅 (1)
	    
	    4. 로그인 성공 시 페이지 이동 전에 getUserInfo()를 호출하여
	     로그인을 성공한 회원의 모든 정보를 받아와서 세션에 저장해 주세요.
	    ( 세션 이름: user, 저장할 값: 로그인 성공한 회원의 UserVO 객체)
	    서블릿 클래스에서 세션을 사용하려면 request.getSession()을 통해
	    세션 객체를 받아와야 한다. (type: HttpSession 인터페이스 타입)
	    */
		
		String id = request.getParameter("id"); //파라미터 값 얻어오기
		String pw = request.getParameter("pw");
		
		UserDAO dao = UserDAO.getInstance(); // DAO 주소값 받아오기
		response.setContentType("text/html; charset=UTF-8");
		String htmlCode; //
		
		int result =  dao.userCheck(id, pw); // usercheck가 전달하는 값은 int
		
		try {
			PrintWriter out = response.getWriter(); //개체의 형식화된 표현을 텍스트 출력 스트림에 출력한다
			if(result == -1) {
				htmlCode =  "<script>\r\n"
                        + "alert('아이디가 존재하지 않습니다.');\r\n"
                        + "location.href='/MyWeb/loginPage.user';\r\n" //로그인페이지로 이동
                        + "</script>";
				out.print(htmlCode);
				out.flush(); // 밖으로 밀어내 줌.
			} else if(result == 0) {
				htmlCode = "<script>\r\n"
                        + "alert('비밀번호가 틀렸습니다.');\r\n"
                        + "history.back();\r\n" //뒤로가기
                        + "</script>";
				out.print(htmlCode);
				out.flush(); // 밖으로 밀어내 줌.
			} else {// 로그인 성공시
				UserVO vo = dao.getUserInfo(id); 
				HttpSession session = request.getSession();
				session.setAttribute("user", vo); // 세션에 로그인성공한 회원의 모든정보를 저장.
				response.sendRedirect("/MyWeb/myPage.user"); //myPage로 리다이렉팅
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
