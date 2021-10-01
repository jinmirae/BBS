<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 보기</title>
<style type="text/css">
table { border-collapse: collapse;}
td { border: 1px solid blue;}
th { color: white; background-color: black; border: 1px solid yellow;}
</style>
</head>
<body>
	<input type=hidden id=bbs_id value='${post.bbs_id}'>
	<table align=center valign=top>
		<tr><td colspan="2">
			<table>
				<tr><td>제목</td><td>${post.title}</td></tr>
				<tr><td>내용</td><td>${post.content}</td></tr>
				<tr><td>작성자</td><td>${post.writer}</td></tr>
				<tr><td>작성시각</td><td>${post.created}</td></tr>
				<tr><td>수정시각</td><td>${post.updated}</td></tr>
			</table>
			</td></tr>
			<tr><td><input type="button" value="수정" id=btnUpdate></td>
			<td align=right><input type="button" id=btnDelete value="삭제" ></td></tr>
	</table>
</body>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
$(document)
.on('click','#btnDelete',function(){
		let bbs_id=$('#bbs_id').val();
		console.log('bbs_id ['+bbs_id+']');//게시물 고유번호 전달 확인하는 디버깅
		document.location="/delete/"+bbs_id;
		return false;
})
.on('click','#btnUpdate',function(){
		let bbs_id=$('#bbs_id').val();
		console.log('bbs_id ['+bbs_id+']');//게시물 고유번호 전달 확인하는 디버깅
		document.location="/update/"+bbs_id;
		return false;
})
</script>
</html>