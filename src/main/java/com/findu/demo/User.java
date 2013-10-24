package com.findu.demo;

public class User {
	private String openid = "";// qq returned id
	private String ownid = ""; // we created id
	private String userid = "";
	private String user = "";
	private String pwd = "";
	private String name = "";
	private String picture = "";
	private int frindsid = -1;
	private int recordid = -1;

	public User() {

		
	}
	
	public User(String user, String pwd, String name, String picture){
		this.user = user;
		this.pwd = pwd;
		this.name = name;
		this.picture = picture;
	}
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}


}