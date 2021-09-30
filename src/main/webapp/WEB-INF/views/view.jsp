<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 보기</title>
</head>
<body>
	<input type=hidden id=bbs_id value='${post.bbs_id}'>
	<table>
		<tr><td>제목</td><td>${post.title}</td></tr>
		<tr><td>내용</td><td>${post.content}</td></tr>
		<tr><td>작성자</td><td>${post.writer}</td></tr>
		<tr><td>작성시각</td><td>${post.created}</td></tr>
		<tr><td>수정시각</td><td>${post.updated}</td></tr>
	</table>
</body>
</html>