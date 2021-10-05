package com.human.app;

public interface iMember {
	void doSignin(String realname,String userid,String passcode);//회원가입
	int doCheckUser(String id, String pass);
}
