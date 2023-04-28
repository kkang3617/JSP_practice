<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

	<style>
	
	table {
		width: 800px;
	}
	
	tr {
		text-align: center;
	}
	
	</style>

</head>
<body>
	<div align="center">	
		<form action="req_album_result.jsp"> <!-- 여기로 submit된 값이 넘어감. -->
			<table border=1>
				<tr>
					<th></th>
					<th>앨범 커버</th>
					<th>가수</th>
					<th>앨범 제목</th>
					<th>발매일</th>
				</tr>	
				<tr>
					<td><input type="radio" name="select" value="s1"></td>
					<!-- 라디오는 s1,s2 name 같아야함 / name을 다르게 하면 다른 그룹으로 봄. -->
					<td><img src="ive.png" alt="ivealbum1" width="100px" height="100px"></td>
					<td>IVE (아이브)</td>
					<td>I AM</td>
					<td>2023.04.10</td>
				</tr>
				<tr>
					<td><input type="radio" name="select" value="s2"></td>
					<td><img src="ive.png" alt="ivealbum1" width="100px" height="100px"></td>
					<td>IVE (아이브)</td>
					<td>I AM</td>
					<td>2023.04.10</td>
				</tr>
				<tr>
					<td colspan=5><input type="submit" value="확인"></td>
				</tr>
			</table>
		</form>
	</div>
	
</body>
</html>