<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
    
    String select = request.getParameter("select");
    
    %>
    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<style>
	body {
		text-align: center;
	}
	</style>

</head>
<body>

	<% 
		if(select.equals("s1")) {
	%>			
			<h1>선택하신 앨범 정보</h1>
		
			<hr>
			
			<p>
			당신이 선택하신 앨범은 IVE(아이브)의 <strong> I AM </strong> 입니다.
			</p>
				
			<hr>
			
			<h2>타이틀 곡 뮤직비디오</h2>
		
			<iframe width="800" height="600" src="https://www.youtube.com/embed/6ZUIwj3FgUY?autoplay=1" title="IVE 아이브 &#39;I AM&#39; MV" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>

	<% } else {%>
			<h1>선택하신 앨범 정보</h1>

			<hr>
			
			<p>
			당신이 선택하신 앨범은 IVE(아이브)의 <strong> Kistch </strong> 입니다.
			</p>
			
			<hr>
			
			<h2>타이틀 곡 뮤직비디오</h2>
			
			<iframe width="800" height="600" src="https://www.youtube.com/embed/pG6iaOMV46I" title="IVE 아이브 &#39;Kitsch&#39; MV" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" allowfullscreen></iframe>	

	<% } %>

	
</body>
</html>