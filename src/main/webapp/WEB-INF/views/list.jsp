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
thead { pointer-events : none;}
td { border: 1px solid blue;}
td:hover { cursor: pointer; color: gray;}
th { color: white; background-color: black; border: 1px solid yellow;}
#box { width: 750px; height: 500px; align:center;}
#btnNew { margin:10px 0px 0px 125px;}
</style>
</head>
<body>
<div id=box>
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
		<input type="button" value="새글쓰기" id=btnNew>
	</div><!-- box -->
</body>

<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
$(document)
.on('click','tr',function(){
	let bbs_id=$(this).find('td:eq(0)').text();//find('td:eq(0)')은 0번째 테이블 정보
	console.log('bbs_id ['+bbs_id+']');//게시물 고유번호 잘 전달되는지 확인하는 디버깅
	document.location="/view/"+bbs_id;//document.location(페이지이동쿼리)이동되는페이지경로 view의 몇번째 게시물 예)/view/3
	return false;
})
.on('click','#btnNew',function(){
	document.location="/new";//document.location(페이지이동쿼리)
	return false;
})

</script>
	
</html>