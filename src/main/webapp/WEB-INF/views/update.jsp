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
	<form method=POST action="/app/update" enctype="multipart/form-data">
		<input type=text id=bbs_id name=bbs_id value='${update.bbs_id}'>
		<table>
			<tr><td>제목</td><td><input type=text id=title name=title value='${update.title}'></td></tr>
			<tr><td valign=top>내용</td><td><textarea id=content name=content rows=20 cols=60>${update.content}</textarea></td></tr>
			<tr><td>작성자</td><td><input type=text id=writer name=writer value='${update.writer}'></td></tr>
			<tr><td>업로드화일</td><td><input type=file name=file id=ufile>
			<tr><td colspan=2><input type=submit value='글 등록'>&nbsp;
				<input type=button id=btnDelete value='취소(목록보기)'>
		</table>
	</form>
</body>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
</script>
</html>