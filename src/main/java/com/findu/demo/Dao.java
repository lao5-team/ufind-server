package com.findu.demo;

import java.sql.*;

public class Dao {
	private Connection mConn;
	private PreparedStatement mPstat;
	String mSql = "";
	public String mSql_DBName = "findu";
	public String mSql_Url = "jdbc:mysql://localhost:3306";
	public String mUsers = "root";
	public String mPwd = "mysql";

	public Dao() {
		String driver = "com.mysql.jdbc.Driver";
		//String url = "jdbc:mysql://localhost/findu";
		try {
			Class.forName(driver);
			try {
				System.out.println("class load over");
				mConn = DriverManager.getConnection(mSql_Url, mUsers, mPwd);
				//mConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/findu?user=root&password=");
			} catch (SQLException e) {
				System.out.println("connect e");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * yonghu zhuce
	 */
	public boolean login(User user) throws SQLException {
		mConn = DriverManager.getConnection(mSql_Url + "/" + mSql_DBName
				+ "?user=" + mUsers + "&password=" + mPwd
				+ "&useUnicode=true&characterEncoding=gb2312");
		System.out.println("dao login");
		boolean i = false;
		mSql = "select * from findu where openid=?";

		mPstat = mConn.prepareStatement(mSql);
		System.out.println(user.getOpenId());
//		System.out.println(user.getPwd());
		mPstat.setString(1, user.getOpenId());
//		mPstat.setString(2, user.getPwd());
		System.out.println("prepareStatement");
		ResultSet rs1 = (ResultSet) mPstat.executeQuery();	
		
		if (rs1.next()) {
			System.out.println("login ok");
			i = true;
			rs1.close();
			mPstat.close();
		} else {
			i = false;
			addUser(user);
//			System.out.println("login fail");
//			System.out.println(rs1.getString("openid").length());
//			System.out.println(rs1.getString("password"));
			rs1.close();
			mPstat.close();
		}
		mConn.close();
		return i;
	}

	/**
	 * 
	 */
	public boolean addUser(User user) {
		try {
			mConn = DriverManager.getConnection(mSql_Url + "/" + mSql_DBName
					+ "?user=" + mUsers + "&password=" + mPwd
					+ "&useUnicode=true&characterEncoding=gb2312");
			mSql = "insert into findu(openid,ownid,userid,pwd,friendsis,recoredid,picture) values(?,?,?,?,?,?,?)";
			mPstat = mConn.prepareStatement(mSql);
			mPstat.setString(1, user.getOpenId());
			mPstat.setString(2, user.getOwnId());
			mPstat.setString(3, user.getUser());
			mPstat.setString(4, user.getPwd());
			mPstat.setInt(5, user.getFriendsId());
			mPstat.setInt(6, user.getRecordId());
			mPstat.setString(7, user.getPicture());

			mPstat.executeUpdate();
			mPstat.close();
			mConn.close();
			return true;

		} catch (SQLException e) {
			System.out.println("add e");
			e.printStackTrace();
		}
		
		return false;

	}
}
