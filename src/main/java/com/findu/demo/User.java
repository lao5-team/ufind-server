package com.findu.demo;

public class User {

	private String openid = "";// qq returned id
	private String ownid = ""; // we created id
	private String userid = "";
	private String pwd = "";
	private String picture = "";
	private int frindsid = -1;
	private int recordid = -1;
 
	public User() {

	}

	public User(String openid, String ownid, String userid, String pwd,
			String picture) {
		this.openid = openid;
		this.ownid = ownid;
		this.userid = userid;
		this.pwd = pwd;
		this.picture = picture;
	}

	public String getOpenId() {
		return openid;
	}

	public String getOwnId() {
		return ownid;
	}

	public void setOwnId(String ownid) {
		this.ownid = ownid;
	}

	public String getUser() {
		return userid;
	}

	public void setUser(String user) {
		this.userid = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public int getFriendsId() {
		return frindsid;
	}
	
	public int getRecordId() {
		return recordid;
	}

}