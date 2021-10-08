<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
<div id=loginbox>
	<h1>로그인페이지</h1>
		<form method="post" action="/app/check_user" id="goLogin">
	                <label class="loginid">ID</label>
	                <input type="text" name=id placeholder="아이디"/><br>
	                <label class="Password">Password</label>
	                <input type="text" name=pass placeholder="비밀번호"/>
					<input type="submit" value="로그인" ></input><br>
	            <span class="form-footer">회원계정이 없습니까? <a href="/app/newbie">회원가입</a></span>
	            	<a href="/app/list/1">목록보기</a>
		</form>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
$(document)
.on('submit','#goLogin',function() {
	let pstr=$.trim($('input[name=id]').val());
	console.log('bbs_id ['+bbs_id+']');
	$('input[name=id]').val(pstr);
	pstr=$.trim($('input[name=pass]').val());
	console.log('pass ['+pass+']');
	$('input[name=pass]').val(pstr);
	if($('input[name=id]').val()==''){
		alert('로그인아이디를 입력하시오.');
		return false;
	}
	if($('input[name=pass]').val()==''){
		alert('비밀번호를 입력하시오.');
		return false;
	}
	return false;
})
</script>
</html>