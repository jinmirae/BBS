package com.human.app;

public class NEWbielist {
	private String realname;
	private String userid;
	private String passcode;
	private String mobilenum;
	
	public NEWbielist() {}
	public NEWbielist(String realname, String userid, String passcode, String mobilenum) {
		this.realname = realname;
		this.userid = userid;
		this.passcode = passcode;
		this.mobilenum = mobilenum;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}
	public String getMobilenum() {
		return mobilenum;
	}
	public void setMobilenum(String mobilenum) {
		this.mobilenum = mobilenum;
	}
	
	
}
