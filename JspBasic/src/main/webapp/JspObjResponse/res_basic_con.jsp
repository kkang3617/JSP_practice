<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%--
    	여기는 전달된 요청이 어떤 요청인지를 확인 후
    	그 요청에 맞는 페이지로 이동하는 것을 전담하는 Controller 페이지이다.
    	그렇기 때문에 이 페이지는 어떠한 화면을 사용자에게 응답하는 페이지가 아니다.
    	자바 코드를 통해 요청 처리 후 적절한 페이지로 이동시키는 역할을 담당한다.
    	
     --%>
    
    <%
    	int age = Integer.parseInt(request.getParameter("age"));
    	//리퀘스트 객체한테 age라는 객체를 받아서 바로 int 정수형으로 바꿔버림
    	
    	if(age >= 20) {
    		//내장객체 response가 제공하는 메서드 sendRedirect()를 통해
    		//원하는 페이지로 강제 이동할 수 있다.
    		//괄호 안에 이동시킬 페이지의 URL을 문자열로 적어주면 된다.
    		response.sendRedirect("res_basic_ok.jsp");
    	} else {
    		response.sendRedirect("res_basic_no.jsp");
    	}
    	
    	
    %>