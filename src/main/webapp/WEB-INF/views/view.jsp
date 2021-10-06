<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<c:if test="${userid eq post.writer}">
				<tr><td><input type="button" value="수정" id=btnUpdate></td>
				<td align=right><input type="button" id=btnDelete value="삭제" ></td></tr>
			</c:if>
			<c:if test="${userid ne ''}">
			<tr id=curTR>
				<td>
					<table style='width:100%;'>
					<tr>
						<td><textarea rows="3" cols="20" id=reply_content></textarea></td>
						<td id=btnAddReply style='background-color: gray'>댓글등록</td>
					</tr>
					</table>
				</td>
			</tr>
			</c:if>
			<c:forEach items="${reply_list}" var="reply">
				<tr><td>
				<br><br><hr>
				<table style='width: 100%;'>
					<tr><td>${reply.content}</td></tr>
					<tr><td>${reply.writer}&nbsp;[${reply.created}]</td></tr>
					<tr><td align="right">
						<c:if test="${userid eq reply.writer}">
							<input type=button id=btnUpdateReply value="수정" reply_id='${reply_id}'>
							<input type=button id=btnDeleteReply value="삭제" reply_id='${reply_id}'>
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
		
		location.reload();
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
	$.post('http://localhost:8080/app/ReplyControl',
			{optype:'delete',reply_id:reply_id},function(result){
		if(result=="ok") {
			thisButton.closest('table').closest('tr').remove();
			}
		},'text');
})
</script>
</html>