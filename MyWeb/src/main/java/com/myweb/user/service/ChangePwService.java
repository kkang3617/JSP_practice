package com.myweb.user.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserDAO;
import com.myweb.user.model.UserVO;

public class ChangePwService implements IUserService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		/*
	    1. 폼 데이터 처리 (기존 비번, 변경 비번)
	    2. dao주소값을 받아오시고, userCheck()를 활용하여
	     기존 비번과 아이디 정보를 바탕으로 해당 비밀번호가 일치하는지를 검사.
	     (id는 세션에서 구해옵니다.)
	    
	    3. 기존 비밀번호가 일치한다면 비밀번호 변경 메서드 changePassword() 호출.
	    4. "비밀번호가 정상적으로 변경되었습니다." 경고창 출력 후 mypage 이동.
	    5. 현재 비밀번호가 불일치 -> "현재 비밀번호가 다릅니다." 경고창 출력 후 뒤로가기.
	    */

		String oldPw = request.getParameter("old_pw"); // 기존비번 
		String newPw = request.getParameter("new_pw"); // 변경비번 가져오기
		
		UserDAO dao = UserDAO.getInstance(); // dao 주소값 받아오기
		response.setContentType("text/html; charset=UTF-8");
		String htmlCode;
		PrintWriter out = null;
		
		HttpSession session = request.getSession();
		
		UserVO user = (UserVO) session.getAttribute("user"); //세션에서 user를 받아옴
		
		int result = dao.userCheck(user.getUserId(), oldPw); //getUserId =세션에서 id받아온것 
		
		try {
			out = response.getWriter();
			if(result == 1 ) {  // 기존 비밀번호가 일치한다면~
				
				dao.changePassword(user.getUserId(), newPw);
				htmlCode =  "<script>\r\n"
						+ "alert('비밀번호가 정상적으로 변경되었습니다.');\r\n"
						+ "location.href='/MyWeb/myPage.user';\r\n" 
						+ "</script>";
				out.print(htmlCode);
				out.flush(); // 밖으로 밀어내 줌.
			} else {  // 기존 비밀번호와 일치하지 않다면~
				htmlCode = "<script>\r\n"
						+ "alert('현재 비밀번호가 틀렸습니다.');\r\n"
						+ "history.back();\r\n" //뒤로가기
						+ "</script>";
				out.print(htmlCode);
				out.flush(); // 밖으로 밀어내 줌.
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.close();
		}
		
	}

}
