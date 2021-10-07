<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 보기</title>
<style type="text/css">
#main { border-collapse: collapse;}
#main td { border: 1px solid blue;}
th { color: white; background-color: black; border: 1px solid yellow;}
</style>
</head>
<body>

	<c:if test="${loggined eq '0' }">
		<input type="button" value="로그인" id=btnLogin>
	</c:if>
	<c:if test="${loggined eq '1' }">
		${userid}님 환영합니다.
		<a href="/app/logout" id=btnLogout>로그아웃</a>
		<a href="/app/list" id=btnback>목록보기</a>
	</c:if>
	<input type=hidden id=bbs_id value='${post.bbs_id}'>
	<table align=center valign=top>
		<tr><td colspan="2">
			<table id=main>
				<tr><td>제목</td><td>${post.title}</td></tr>
				<tr><td>내용</td><td>${post.content}</td></tr>
				<tr><td>작성자</td><td>${post.writer}</td></tr>
				<tr><td>작성시각</td><td>${post.created}</td></tr>
				<tr><td>수정시각</td><td>${post.updated}</td></tr>
			</table>
				</td></tr>
			<c:if test="${userid eq post.writer}">
				<tr><td><input type="button" value="수정" id=btnUpdate></td>
				<td align=right><input type="button" id=btnDelete value="삭제" ></td></tr>
			</c:if>
			<c:if test="${userid ne ''}">
			<c:if test="${userid ne null}">
			<tr id=curTR>
				<td>
					<table style='width:100%;'>
					<tr>
						<td><textarea rows="4" cols="40" id=reply_content></textarea></td>
						<td id=btnAddReply style='background-color: gray'>댓글등록</td>
					</tr>
					</table>
				</td>
			</tr>
			</c:if>
			</c:if>
			<c:forEach items="${reply_list}" var="reply">
				<tr><td>
				<table style='width: 100%;'>
					<tr><td>${reply.content}</td></tr>
					<tr><td>${reply.writer}&nbsp;[${reply.updated}]</td></tr>
					<tr><td align=right>
						<c:if test="${userid eq reply.writer}">
							<input type=button id=btnUpdateReply value="수정" reply_id='${reply.reply_id}'>
							<input type=button id=btnDeleteReply value="삭제" reply_id='${reply.reply_id}'>
						</c:if>
					<tr><td><hr></td></tr>
				</table>
				</td></tr>
			</c:forEach>
	</table>	
</body>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
$(document)
.on('click','#btnNew',function(){
	document.location="/app/new";//document.location(페이지이동쿼리)
	return false;
})
.on('click','#btnLogin',function(){
	document.location="/app/login";//document.location(페이지이동쿼리)
	return false;
})
.on('click','#btnLogout',function(){
	if(confirm("정말로 로그아웃할까요?")) return true
	else return false;
})
.on('click','#btnDelete',function(){
		let bbs_id=$('#bbs_id').val();
		console.log('bbs_id ['+bbs_id+']');//게시물 고유번호 전달 확인하는 디버깅
		document.location="/app/delete/"+bbs_id;
		return false;
})
.on('click','#btnUpdate',function(){
		let bbs_id=$('#bbs_id').val();
		console.log('bbs_id ['+bbs_id+']');//게시물 고유번호 전달 확인하는 디버깅
		document.location="/app/update/"+bbs_id;
		return false;
})
.on('click','#btnAddReply',function(){
	let pstr=$('#reply_content').val();
	pstr=$.trim(pstr);
	console.log(pstr);
	if(pstr=='') return false;
	$.post('http://localhost:8080/app/ReplyControl',
			{optype:'add',content:pstr,bbs_id:$('#bbs_id').val()},function(result){
		console.log(result);
		if(result=="fail") return false;
		
		location.reload();//새로고침!!!
		let str='<tr><td>'+pstr+'</td></tr>';
		$('#curTR').after(str);
		$('#reply_content').val('');
	},'text')
	return false;	
})
.on('click','#btnDeleteReply',function(){
	if(!confirm("정말로 지울까요?")) return false;
	let reply_id=$(this).attr('reply_id');
	let thisButton=$(this);
	location.reload();
	$.post('http://localhost:8080/app/ReplyControl',
			{optype:'delete',reply_id:reply_id},function(result){
		if(result=="ok") {
			thisButton.closest('table').closest('tr').remove();//thisButton table과 가장 가까운 tr
			}
		},'text');
	return false;
})
.on('click','#btnUpdateReply',function(){
	let reply_id=$(this).attr('reply_id');
	let content=$(this).closest('table').find('tr:eq(0) td:eq(0)').text();
	$(this).closest('table').find('tr:lt(2)').hide();
	let str=('<textarea rows=5 cols=50 id=reply_update>'+content+
			'</textarea><br>'+
			'<input type=button id=btnComplete value="댓글수정완료" reply_id="'+reply_id+'">&nbsp'+
			'<input type=button value="취소" onclick="location.reload();">');
	$(this).closest('td').html(str);
	return false;
})
.on('click','#btnComplete',function(){
	let pstr=$('#reply_update').val();
	pstr=$.trim(pstr);
	console.log(pstr);
	if(pstr=='') return false;
	let rid=$(this).attr('reply_id');
	
	$.post('http://localhost:8080/app/ReplyControl',
			{optype:'update',content:pstr,reply_id:rid},function(result){
		console.log(result);
		
		if(result=="fail") return false;
		
		location.reload();
	},'text')
	return false;	
})
</script>
</html>