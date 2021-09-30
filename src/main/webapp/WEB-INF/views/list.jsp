<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글목록</title>
<style type="text/css">
table { border-collapse: collapse;}
td { border: 1px solid blue;}
td:hover { cursor: pointer; color: gray;}
th { color: white; background-color: black; border: 1px solid yellow;}
</style>
</head>
<body>
	<h2>게시판목록</h2>
<table size="10" id="goList" style="width: 700px;"><!-- "c"taglib 불러오기는 필수!! -->
	<thead>
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>내용</th>
				<th>아이디</th>
				<th>작성시각</th>
				<th>수정시각</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${letlist}" var="bbs">
				<option value="${bbs.bbs_id}">
					<tr><td>${bbs.bbs_id}</td><td> ${bbs.title}</td><td> ${bbs.content}</td><td>${bbs.writer}</td><td>${bbs.created}</td><td>${bbs.updated}</td></tr>
				</option>
			</c:forEach>
		</tbody>
</table>
</body>

<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
$(document)
.on('click','tr',function(){
	let bbs_id=$(this).find('td:eq(0)').text();//find('td:eq(0)')은 0번째 테이블 정보
	console.log('bbs_id ['+bbs_id+']');
	document.location="/view/"+bbs_id;
	return false;
})
</script>


<%--
	 		<table size="10" id="goList" style="width: 300px;"><!-- "c"taglib 불러오기는 필수!! -->
			<c:forEach items="${letlist}" var="bbs">
				<option value="${bbs.bbs_id}">
					${bbs.bbs_id}, ${bbs.title}, ${bbs.content}, ${bbs.writer}, ${bbs.created}, ${bbs.updated}
				</option>
			</c:forEach>
			</table> 
--%>
	
	
</html>