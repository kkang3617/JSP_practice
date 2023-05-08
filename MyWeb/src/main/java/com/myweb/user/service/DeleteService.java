package com.myweb.user.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.user.model.UserDAO;
import com.myweb.user.model.UserVO;

public class DeleteService implements IUserService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String pw = request.getParameter("check_pw");
		UserDAO dao = UserDAO.getInstance(); // UserDAO의 주소값을 가져옴.
		
		// 로그인을 한 상태라 form입력할 때 id 값을 받지 않는다. 그래서 세션에서 아이디를 얻어와야한다.
		UserVO vo = (UserVO) request.getSession().getAttribute("user"); // 얘못함
										//-> 세션에서 user를 받아옴.
		
		response.setContentType("text/html; charset=UTF-8"); //alert 메세지 표현할때 필요함.
		
		int result = dao.userCheck(vo.getUserId(), pw);
		
		PrintWriter out = null;
		String htmlCode;
		
		try {
			out = response.getWriter();
			
			if( result == 1 ) { // 아디 비번 일치할 경우
				dao.deleteUser(vo.getUserId());
				request.getSession().removeAttribute("user"); // 세션에서 user를 삭제. 또는 .invalidate();
				htmlCode =  "<script>\r\n"
                        + "alert('정상적으로 탈퇴되었습니다.');\r\n"
                        + "location.href='/MyWeb';\r\n" //홈으로 이동
                        + "</script>";
				
			} else { // 아디 비번 일치하지 않을 경우
				htmlCode =  "<script>\r\n"
                        + "alert('비밀번호를 다시 확인하세요');\r\n"
                        + "history.back();\r\n" //뒤로가기
                        + "</script>";
				
			} 
			
			out.print(htmlCode);
			out.flush(); // 밖으로 밀어내 줌.
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
		
	}

}
