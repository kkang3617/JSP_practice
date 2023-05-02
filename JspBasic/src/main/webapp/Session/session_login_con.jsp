<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<%
		request.setCharacterEncoding("utf-8"); //한글 박살나서 해줌.
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String nick = request.getParameter("nick");
		
		if(id.equals("abc1234") && pw.equals("aaa1111")) {
			session.setAttribute("user_id", id);
			session.setAttribute("user_nick", nick);
			response.sendRedirect("session_welcome.jsp");
			session.setMaxInactiveInterval(60) ; //초 단위 세션의 유효기간 설정.
		} else {
	%>	
		<script>
			alert('로그인에 실패했습니다.');
			// == sendRedirect
			location.href="session_login.jsp";
		</script>
		
		<% } %>
