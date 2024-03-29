<%@page import="java.util.Arrays"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- 	<%
		int total = 0;
		for(int i=1; i<=100; i++) {
			total += i;
		}
		out.print("<h4>1부터 100까지의 누적합: " + total + "</hr>");	
	%> --%>
	
	<%--
		<c:set> 태그는 변수의 생성과 동시에, 이미 동일한 이름의 데이터가 존재한다면
		기존의 데이터를 지목해서 값을 변경할 때도 사용한다.
		마치 setAttribute()에서 이미 존재하는 이름을 지목하여 값을 변경하는 것처럼.
	 --%>
	<c:set var="total" value="0"/> <!-- total 선언 -->
	<c:forEach var="i" begin="1" end="100" step="1">
		<c:set var="total" value="${total + i}"/> <!-- 기존에있던 total 지목을 위한것. -->
	</c:forEach>
	
	<h4>1부터 100까지의 누적합: ${total}</h4>
	
	<hr>
	
	<h4>구구단 4단</h4>
	<%-- <% for(int hang=1; hang<=9; hang++) { %>
		4 X <%=hang %> = <%= 4*hang %> <br>
	<% } %> --%>
	
	<c:forEach var="hang" begin="1" end="9"> <%-- step을 생략 시 자동을 1로 처리됨. --%>
		4 X ${hang} = ${4*hang} <br>	
	</c:forEach>
	
	<hr>
	
	<%-- 구구단을 2~9단까지 출력하는데, 짝수단만 출력해보세요. (jstl) --%>
	<c:forEach var="dan" begin="2" end="9" step="2">
		구구단 ${dan}단 <br>
		<c:forEach var="hang" begin="1" end="9" step="1">
			${dan} X ${hang} = ${dan*hang} <br>
		</c:forEach>
	</c:forEach>
	
	<hr>
	
	<c:forEach var="dan" begin="2" end="9">
		<c:if test="${dan % 2 == 0}">
			구구단 ${dan}단 <br>		
			<c:forEach var="hang" begin="1" end="9" step="1">
				${dan} X ${hang} = ${dan*hang} <br>
			</c:forEach>
		</c:if>
	</c:forEach>
	
	<hr>
	
	<h2>배열이나 컬렉션 내부의 값을 출력</h2>
	
	<c:set var="arr" value="<%=new int[] {1, 3, 5, 7, 9} %>"></c:set>
	
	<c:forEach var="n" items="${arr}">
		${n} &nbsp; <!-- nbsp 가로로 공백 -->
	</c:forEach>
	
	<hr>
	
	<c:set var="list" value='<%=Arrays.asList("가", "나", "다", "라")%>'/>
	
	<c:forEach var="str" items="${list}">
		${str} <br>
	</c:forEach>
	
</body>
</html>