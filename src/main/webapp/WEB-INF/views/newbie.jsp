<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="wowfadeIn" class="wow fadeIn">
        <h1>Welcome sign up</h1>
        <div class="actions">
	                    <form method="post" action="/app/signin" id="frmLogin">
	                        <div class="form-group">
	                        	<label>이름</label>
	                            <input type="text" id="realname" name=realname placeholder="이름"/><br>
	                            <label>로그인ID</label>
	                            <input type="text" id="userid" name=userid placeholder="아이디"/><br>
	                            <label>비밀번호</label>
	                            <input type=password id="passcode" name=passcode placeholder="비밀번호"/><br>
	                            <label>비밀번호확인</label>
	                            <input type=password id="passcode2" name=passcode2 placeholder="비밀번호확인"/><br>
	                            <button type="submit" id="btnSignin" >가입완료</button>
	                        </div>
	                        <span>이미계정이있을경우 -><a href="/app/login">로그인 바로가기</a></span>
	                    </form>
        </div>
</body>
<script src="https://code.jquery.com/jquery-3.5.0.js"></script>
<script>
$(document)
.on('submit','#frmLogin',function(){
	if($('input[name=realname]').val()==''){
		alert('이름을 입력하시오.');
		return false;
	}
	if($('input[name=userid]').val()==''){
		alert('로그인아이디를 입력하시오.');
		return false;
	}
	if($('input[name=passcode1]').val()==''){
		alert('비밀번호를 입력하시오.');
		return false;
	}
	if($('input[name=passcode]').val()!==$('input[name=passcode2]').val()){
		alert('비밀번호가 일치하지 않습니다.');
		return false;
	}
	return true;//!!!!!!!!!!!유효성검사 마무리는 무조건 ture!!!!!!!!!!!!!!!!!
})
</script>
</html>