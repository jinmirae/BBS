<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>새글쓰기</title>
</head>
<body>
<form method=POST action="/app/save" id=frmNew enctype="multipart/form-data"><!-- enctype="multipart/form-data" 이미지 업로드 -->
<table>
<tr><td>제목</td><td><input type=text name=title></td></tr>
<tr><td valign=top>내용</td><td><textarea name=content rows=20 cols=60></textarea></td></tr>
<tr><td>작성자</td><td><input type=text name=writer value='${userid}' readonly></td></tr>
<!-- <tr><td>비밀번호</td><td><input type=password name=passcode></td></tr> -->
<tr><td>업로드화일</td><td><input type=file name=file id=ufile/></td></tr>
<tr><td colspan=2><input type=submit value='글 등록'>&nbsp;
	<input type=button id=btnDelete value='취소(목록보기)'>
</table>
</form>
</body>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
$(document)
.on('click','#btnDelete',function(){
	document.location="/list/1";//document.location(페이지이동쿼리)
	return false;
})
.on('submit','#frmNew',function(){
	let title=$('title').val();
	title=$.trim(title);
})

</script>
</html>